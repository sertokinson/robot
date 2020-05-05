package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.api.RobotController;
import ru.sertok.robot.core.ExecuteApp;
import ru.sertok.robot.core.ScreenShot;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.Status;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.RobotResponse;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<Object> data = testCase.getSteps();
        Image image = testCase.getImage();
        if (image != null) {
            screenShot.setSize(new Point(image.getX(), image.getY()), new Dimension(image.getWidth(), image.getHeight()));
            localStorage.setImages(new ArrayList<>());
        }
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            log.error("Ошибка при создании робота", e);
            return Response.serverError().build();
        }
        if (!executeApp.execute(testCase.getUrl())) {
            log.error("Не удалось запустить приложение!");
            return Response.serverError().build();
        }
        robot.delay(900);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof Mouse) {
                Mouse mouse = (Mouse) data.get(i);
                switch (mouse.getType()) {
                    case PRESSED:
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        break;
                    case MOVED:
                        robot.mouseMove(mouse.getX(), mouse.getY());
                        break;
                    case RELEASED:
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
                if (mouse.isScreenshot())
                    screenShot.make();
                if (i + 1 < data.size()) {
                    if (data.get(i + 1) instanceof Mouse) {
                        robot.delay(((Mouse) data.get(i + 1)).getTime() - mouse.getTime());
                    }
                }
              /*  if (i + 2 < data.size()) {
                    if (data.get(i + 2) instanceof Mouse) {
                        int firstClick = mouse.getTime();
                        int secondClick = ((Mouse) data.get(i + 2)).getTime();
                        if (secondClick - firstClick < 400) {
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            i += 3;
                            continue;
                        }
                    }
                }
                if (i + 1 < data.size()) {
                    if (data.get(i + 1) instanceof Mouse) {
                        int firstClick = mouse.getTime();
                        int secondClick = ((Mouse) data.get(i + 1)).getTime();
                        if (secondClick - firstClick < 400) {
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            i++;
                            continue;
                        }
                    }
                }*/

            }
           /* if (data.get(i) instanceof Keyboard) {
                Keyboard keyboard = (Keyboard) data.get(i);
                robot.delay(200);
                try {
                    int keyEvent = new KeyEvents().getKey(keyboard.getKey());
                    if (keyboard.getType() == Type.PRESSED) {
                        robot.keyPress(keyEvent);
                    } else {
                        robot.keyRelease(keyEvent);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/
        }
        if (checkResult(testCaseName)) {
            return Response.ok(Status.SUCCESS).build();
        } else return Response.ok(Status.ERROR).build();
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
        for (Image expectedImage : expectedImages) {
            boolean success = false;
            for (Image actualImage : actualImages) {
                if (compare(actualImage.getImage(), expectedImage.getImage())) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                countError++;
            }
        }
        database.save(actualImages);
        // допускаем максимум 10 не совпадений
        return countError <= 10;
    }

    private boolean compare(byte[] actual, byte[] expected) {
        try {
            BufferedImage expectedImage = ImageIO.read(new ByteArrayInputStream(expected));
            BufferedImage actualImage = ImageIO.read(new ByteArrayInputStream(actual));
            if (actualImage.getWidth() != expectedImage.getWidth() || expectedImage.getHeight() != actualImage.getHeight()) {
                log.error("Размеры фактического изображения width: {} height: {} не совпадают с размерами ожидаемого изображения width: {} height: {}",
                        actualImage.getWidth(), actualImage.getHeight(), expectedImage.getWidth(), expectedImage.getHeight());
                return false;
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
            // допускаем 10% не совпадение
            return (countIsNotIdentic * 100) / (actualImage.getWidth() * actualImage.getHeight()) <= 10;

        } catch (IOException e) {
            log.error("Не смог преобразовать изображение", e);
            return false;
        }
    }

    private boolean compareColor(Color color1, Color color2) {
        if (Math.abs(color1.getRed() - color2.getRed()) > 10) {
            return false;
        }
        if (Math.abs(color1.getBlue() - color2.getBlue()) > 10) {
            return false;
        }
        return Math.abs(color1.getGreen() - color2.getGreen()) <= 10;

    }

    @Override
    public Response get() {
        log.debug("REST-запрос ../robot/get");
        return Response.ok(new RobotResponse(database.getAll())).build();
    }
}
