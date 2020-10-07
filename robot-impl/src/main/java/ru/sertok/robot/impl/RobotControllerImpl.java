package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.api.RobotController;
import ru.sertok.robot.core.hook.KeyEvents;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.core.service.ScreenShot;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.*;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.data.enumerate.TestStatus;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.data.enumerate.TypePressed;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.*;
import ru.sertok.robot.storage.LocalStorage;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RobotControllerImpl implements RobotController {
    private final Database database;
    private final ScreenShot screenShot;
    private final LocalStorage localStorage;
    private final AppService appService;
    private final KeyEvents keyEvents;

    @Override
    public RobotResponse start(RobotRequest robotRequest) {
        String testCaseName = robotRequest.getTestCase();
        log.info("REST-запрос ../robot/start со значением {}", testCaseName);
        localStorage.invalidateLocalStorage();
        TestCase testCase = database.get(testCaseName);
        if (testCase == null) {
            String error = "Не найден testCase по наименованию: " + testCaseName;
            log.error(error);
            return ResponseBuilder.error(RobotResponse.builder().error(error).build());
        }
        List<BaseData> data = database.getSteps(testCaseName);
        Optional.ofNullable(database.getScreenshotSize(testCaseName)).ifPresent(screenShot::setSize);
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            String error = "Ошибка при создании робота";
            log.error(error, e);
            return ResponseBuilder.error(RobotResponse.builder().error(error).build());
        }
        if (appService.execute(testCase) == Status.ERROR) {
            String error = "Не удалось запустить приложение!";
            log.error(error);
            return ResponseBuilder.error(RobotResponse.builder().error(error).build());
        }
        for (int i = 0; i < data.size(); i++) {
            BaseData baseData = data.get(i);
            if (baseData != null) {
                if (i == 0)
                    robot.delay((data.get(i)).getTime());
                if (baseData instanceof Mouse) {
                    Mouse mouse = (Mouse) baseData;
                    switch (mouse.getType()) {
                        case PRESSED:
                            if (mouse.getCount() > 1) {
                                for (int j = 0; j < mouse.getCount() - 1; j++) {
                                    robot.mousePress(InputEvent.BUTTON1_MASK);
                                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                    robot.mousePress(InputEvent.BUTTON1_MASK);
                                }
                            } else {
                                robot.mousePress(getButton(mouse.getTypePressed()));
                            }
                            break;
                        case WHEEL:
                            robot.mouseWheel(mouse.getWheel());
                            break;
                        case MOVED:
                            robot.mouseMove(mouse.getX(), mouse.getY());
                            break;
                        case RELEASED:
                            robot.mouseRelease(getButton(mouse.getTypePressed()));
                    }
                }
                if (baseData instanceof Keyboard) {
                    Keyboard keyboard = (Keyboard) baseData;
                    try {
                        int keyEvent = keyEvents.getKey(keyboard.getKey());
                        if (keyboard.getType() == Type.PRESSED) {
                            robot.keyPress(keyEvent);
                        } else {
                            robot.keyRelease(keyEvent);
                        }
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        log.error("Нет такой клавиши KeyEvents {} ", keyboard.getKey(), e);
                        return ResponseBuilder.error(RobotResponse.builder().error("Ошибка при считывании клавиши").build());
                    }
                }
                if (baseData instanceof Image)
                   screenShot.makeOne();
                if (i + 1 < data.size() && data.get(i + 1) != null) {
                    try {
                        robot.delay((data.get(i + 1)).getTime() - baseData.getTime());
                    } catch (IllegalArgumentException e) {
                        log.error("Разница во времени между событиями отрицательна {} на позиции {}", (data.get(i + 1)).getTime() - baseData.getTime(), i);
                    }
                }
            }
        }
        if (checkResult(testCaseName)) {
            database.update(testCaseName, TestStatus.SUCCESS);
            return ResponseBuilder.success(RobotResponse.builder()
                    .testStatus(TestStatus.SUCCESS)
                    .build());
        }
        database.update(testCaseName, TestStatus.ERROR);
        return ResponseBuilder.success(RobotResponse.builder()
                .testStatus(TestStatus.ERROR)
                .build());
    }

    @Override
    public BaseResponse delete(RobotRequest robotRequest) {
        log.info("REST-запрос ../robot/delete со значением {}", robotRequest);
        database.delete(robotRequest.getTestCase());
        return ResponseBuilder.success();
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
            boolean compare = screenShot.compare(actual.getImage(), expectedImages.get(i).getImage());
            if (compare) {
                actual.setAssertResult(true);
            } else {
                actual.setAssertResult(false);
                countError++;
            }
        }
        database.save(actualImages, testCaseName);
        // допускаем максимум 2% не совпадений
        return countError == 0;
    }


    private int getButton(TypePressed type) {
        if (TypePressed.LEFT == type) {
            return InputEvent.BUTTON1_MASK;
        } else {
            return InputEvent.BUTTON3_MASK;
        }
    }

    @Override
    public TestCasesResponse getAll() {
        log.info("REST-запрос ../robot/getAll");
        return ResponseBuilder.success(TestCasesResponse.builder()
                .testCases(database.getAll())
                .build());
    }

    @Override
    public FoldersResponse getFolders() {
        log.debug("REST-запрос ../robot/getFolders");
        return ResponseBuilder.success(FoldersResponse.builder()
                .folders(database.getFolders())
                .build());
    }

    @Override
    public TestCaseResponse get(String testCase) {
        log.debug("REST-запрос ../robot/get/{}", testCase);
        return ResponseBuilder.success(TestCaseResponse.builder()
                .testCase(database.get(testCase))
                .build());
    }
}
