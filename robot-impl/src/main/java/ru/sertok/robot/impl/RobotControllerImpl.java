package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.api.RobotController;
import ru.sertok.robot.core.ExecuteApp;
import ru.sertok.robot.core.ScreenShot;
import ru.sertok.robot.core.hook.KeyEvents;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.*;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.response.RobotResponse;
import ru.sertok.robot.response.TestCasesResponse;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RobotControllerImpl implements RobotController {
    private final Database database;
    private final ScreenShot screenShot;
    private final LocalStorage localStorage;
    private final ExecuteApp executeApp;



    @Override
    public Response start(RobotRequest robotRequest) {
        String testCaseName = robotRequest.getTestCase();
        log.debug("REST-запрос ../robot/start со значением {}", testCaseName);
        TestCase testCase = database.get(testCaseName);
        if (testCase == null) {
            String error = "Не найден testCast по наименованию: " + testCaseName;
            log.error(error);
            return ResponseBuilder.error(error);
        }
        List<BaseData> data = testCase.getSteps();
        Optional.ofNullable(testCase.getImage()).ifPresent(screenShot::setSize);
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            String error = "Ошибка при создании робота";
            log.error(error, e);
            return ResponseBuilder.error(error);
        }
        if (executeApp.execute(testCase.getUrl(), testCase.getPath()) == Status.ERROR) {
            String error = "Не удалось запустить приложение!";
            log.error(error);
            return ResponseBuilder.error(error);
        }
        robot.delay(900);
        for (int i = 0; i < data.size(); i++) {
            BaseData baseData = data.get(i);
            if (baseData != null) {
                if (baseData instanceof Mouse) {
                    Mouse mouse = (Mouse) baseData;
                    switch (mouse.getType()) {
                        case PRESSED:
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            break;
                        case WHEEL:
                            robot.mouseWheel(mouse.getWheel());
                            break;
                        case MOVED:
                            robot.mouseMove(mouse.getX(), mouse.getY());
                            break;
                        case RELEASED:
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                }
                if (baseData instanceof Keyboard) {
                    Keyboard keyboard = (Keyboard) baseData;
                    try {
                        int keyEvent = new KeyEvents().getKey(keyboard.getKey());
                        if (keyboard.getType() == Type.PRESSED) {
                            robot.keyPress(keyEvent);
                        } else {
                            robot.keyRelease(keyEvent);
                        }
                    } catch (IllegalAccessException e) {
                        log.error("Нет такой клавиши в KeyEvents {}", keyboard.getKey(), e);
                        return ResponseBuilder.error("Ошибка при считывании клавиши");
                    }
                }
                if (baseData.isScreenshot())
                    screenShot.make();
                if (i + 1 < data.size() && data.get(i + 1) != null) {
                    try {
                        robot.delay((data.get(i + 1)).getTime() - baseData.getTime());
                    } catch (IllegalArgumentException e) {
                        log.error("Разница во времени между событиями отрицательна {} на позиции {}", (data.get(i + 1)).getTime() - baseData.getTime(), i);
                    }
                }
            }
        }
        if (checkResult(testCaseName))
            return ResponseBuilder.ok(new RobotResponse(Status.TEST_SUCCESS));
        return ResponseBuilder.ok(new RobotResponse(Status.TEST_ERROR));
    }

    @Override
    public Response delete(RobotRequest robotRequest) {
        log.debug("REST-запрос ../robot/delete со значением {}", robotRequest);
        database.delete(robotRequest.getTestCase());
        return ResponseBuilder.ok();
    }

    private boolean checkResult(String testCaseName) {
        List<Image> expectedImages = database.getImages(testCaseName);
        List<Image> actualImages = localStorage.getImages();
        if (CollectionUtils.isEmpty(expectedImages) && !CollectionUtils.isEmpty(actualImages)) {
            log.error("Ожидаемые изображения пустые а фактические нет");
            return false;
        }
        if (!CollectionUtils.isEmpty(expectedImages) && CollectionUtils.isEmpty(actualImages)) {
            log.error("Фактические изображения пустые а ожидаемые нет");
            return false;
        }
        if (CollectionUtils.isEmpty(expectedImages) && CollectionUtils.isEmpty(actualImages)) {
            log.debug("Фактические и ожидаемые изображения пустые");
            return true;
        }
        // количество не совпадающих изображений
        int countError = 0;
        int size = Math.min(expectedImages.size(), actualImages.size());
        for (int i = 0; i < size; i++) {
            Image actual = actualImages.get(i);
            // вычесляем процент совпадения
            int percent = compare(actual.getImage(), expectedImages.get(i).getImage());
            // допускаем 2% не совпадение
            if (percent > 2) {
                actual.setAssertResult(false);
                countError++;
            } else {
                actual.setAssertResult(true);
            }
            actual.setPercent(percent);
        }
        database.save(actualImages, testCaseName);
        // допускаем максимум 2% не совпадений
        return countError == 0;
    }

    private int compare(byte[] actual, byte[] expected) {
        try {
            BufferedImage expectedImage = ImageIO.read(new ByteArrayInputStream(expected));
            BufferedImage actualImage = ImageIO.read(new ByteArrayInputStream(actual));
            if (actualImage.getWidth() != expectedImage.getWidth() || expectedImage.getHeight() != actualImage.getHeight()) {
                log.error("Размеры фактического изображения width: {} height: {} не совпадают с размерами ожидаемого изображения width: {} height: {}",
                        actualImage.getWidth(), actualImage.getHeight(), expectedImage.getWidth(), expectedImage.getHeight());
                return 100;
            }
            // количество не идентичных пикселей
            int countIsNotIdentic = 0;
            for (int i = 0; i < actualImage.getWidth(); i++) {
                for (int j = 0; j < actualImage.getHeight(); j++) {
                    int actualRGB = actualImage.getRGB(i, j);
                    int expectedRGB = expectedImage.getRGB(i, j);
                    Color color1 = new Color((actualRGB >> 16) & 0xFF, (actualRGB >> 8) & 0xFF, (actualRGB) & 0xFF);
                    Color color2 = new Color((expectedRGB >> 16) & 0xFF, (expectedRGB >> 8) & 0xFF, (expectedRGB) & 0xFF);
                    if (!compareColor(color1, color2)) {
                        countIsNotIdentic++;
                    }
                }
            }
            return (countIsNotIdentic * 100) / (actualImage.getWidth() * actualImage.getHeight());

        } catch (IOException e) {
            log.error("Не смог преобразовать изображение", e);
            return 100;
        }
    }

    private boolean compareColor(Color color1, Color color2) {
        if (Math.abs(color1.getRed() - color2.getRed()) > 20) {
            return false;
        }
        if (Math.abs(color1.getBlue() - color2.getBlue()) > 20) {
            return false;
        }
        return Math.abs(color1.getGreen() - color2.getGreen()) <= 20;

    }

    @Override
    public Response getAll() {
        log.debug("REST-запрос ../robot/get");
        return ResponseBuilder.ok(new TestCasesResponse(database.getAll()));
    }
}
