package ru.sertok.robot.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.data.*;
import ru.sertok.robot.data.enumerate.TestStatus;
import ru.sertok.robot.entity.*;
import ru.sertok.robot.mapper.KeyboardMapper;
import ru.sertok.robot.mapper.MouseMapper;
import ru.sertok.robot.mapper.ScreenShotMapper;
import ru.sertok.robot.mapper.TestCaseMapper;
import ru.sertok.robot.service.*;
import ru.sertok.robot.storage.LocalStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Database {
    private final MouseMapper mouseMapper;
    private final ScreenShotMapper screenShotMapper;
    private final KeyboardMapper keyboardMapper;
    private final TestCaseMapper testCaseMapper;

    private final LocalStorage localStorage;

    private final TestCaseService testCaseService;
    private final ImageService imageService;
    private final KeyboardService keyboardService;
    private final MouseService mouseService;
    private final ScreenShotService screenShotService;
    private final SettingsService settingsService;

    public List<TestCase> getAll() {
        List<TestCase> testCases = testCaseService.getAll();
        log.debug("Получаем все тест кейсы из БД {}", testCases);
        return testCases;
    }

    public void update(String testCase, TestStatus status) {
        TestCaseEntity testCaseEntity = testCaseService.get(testCase);
        testCaseEntity.setStatus(status);
        testCaseEntity.setRunDate(new Date());
        testCaseService.save(testCaseEntity);
    }

    public void save() {
        TestCase testCase = localStorage.getTestCase();
        log.debug("Сохраняем тест кейс в БД {}", testCase);
        Optional.ofNullable(testCaseService.get(testCase.getTestCaseName()))
                .ifPresent(testCaseService::delete);
        TestCaseEntity testCaseEntity = testCaseMapper.toTestCaseEntity(testCase);
        testCaseEntity.setStatus(TestStatus.NONE);
        testCaseEntity.setTime(System.currentTimeMillis() - localStorage.getStartTime());
        if (testCase.getIsBrowser() && settingsService.getBrowser(testCase.getAppName()) == null){
            testCaseEntity.setBrowser(settingsService.saveBrowser(testCase));
            testCaseEntity.setUrl(settingsService.saveUrl(testCase.getUrl()));
        }
        else if (settingsService.getDesktop(testCase.getAppName()) == null)
            testCaseEntity.setDesktop(settingsService.saveDesktop(testCase.getAppName(), testCase.getPathToApp()));
        testCaseService.save(testCaseEntity);
        Optional.ofNullable(localStorage.getSize())
                .ifPresent(size -> {
                            ScreenShotEntity screenShotEntity = screenShotMapper.toScreenShotEntity(size);
                            screenShotEntity.setTestCase(testCaseEntity);
                            screenShotService.save(screenShotEntity);
                        }
                );
        List<BaseData> steps = localStorage.getSteps();
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i) instanceof Mouse) {
                MouseEntity mouseEntity = mouseMapper.toMouseEntity((Mouse) steps.get(i));
                mouseEntity.setTestCase(testCaseEntity);
                mouseEntity.setPosition(i);
                mouseService.save(mouseEntity);
            }
            if (steps.get(i) instanceof Keyboard) {
                KeyboardEntity keyboardEntity = keyboardMapper.toKeyboardEntity((Keyboard) steps.get(i));
                keyboardEntity.setPosition(i);
                keyboardEntity.setTestCase(testCaseEntity);
                keyboardService.save(keyboardEntity);
            }
        }
        List<Image> images = localStorage.getImages();
        if (!CollectionUtils.isEmpty(images)) {
            for (int i = 0; i < images.size(); i++) {
                imageService.save(ImageEntity.builder()
                        .testCase(testCaseEntity)
                        .position(i)
                        .photoExpected(images.get(i).getImage())
                        .build());
            }
        }
    }

    public void save(List<Image> images, String testCaseName) {
        log.debug("Сохраняем изображения в БД {}", images);
        TestCaseEntity testCaseEntity = testCaseService.get(testCaseName);
        Optional.ofNullable(imageService.getAll(testCaseEntity)).ifPresent(
                imageEntities -> {
                    for (int i = 0; i < images.size(); i++) {
                        ImageEntity imageEntity = imageEntities.get(i);
                        Image image = images.get(i);
                        imageEntity.setPhotoActual(image.getImage());
                        imageEntity.setAssertResult(image.isAssertResult());
                        imageEntity.setPercent(image.getPercent());
                    }
                    testCaseEntity.setImages(imageEntities);
                    testCaseService.save(testCaseEntity);
                }
        );
    }

    public TestCase get(String testCaseName) {
        log.debug("Получаем тест-кейс из БД по имени: {}", testCaseName);
        return Optional.ofNullable(testCaseService.get(testCaseName))
                .map(testCaseMapper::toTestCase)
                .orElse(null);
    }

    public List<BaseData> getSteps(String testCaseName) {
        List<Object> steps = new ArrayList<>();
        return Optional.ofNullable(testCaseService.get(testCaseName)).map(testCaseEntity -> {
            steps.addAll(testCaseEntity.getMouse());
            steps.addAll(testCaseEntity.getKeyboard());
            BaseData[] stepsResult = new BaseData[steps.size() + 1];
            for (Object step : steps) {
                if (step instanceof MouseEntity) {
                    MouseEntity mouseEntity = (MouseEntity) step;
                    stepsResult[mouseEntity.getPosition()] = mouseMapper.toMouse(mouseEntity);
                }
                if (step instanceof KeyboardEntity) {
                    KeyboardEntity keyboardEntity = (KeyboardEntity) step;
                    stepsResult[keyboardEntity.getPosition()] = keyboardMapper.toKeyboard(keyboardEntity);
                }
            }
            return Arrays.asList(stepsResult);
        }).orElse(null);

    }

    public ScreenshotSize getScreenshotSize(String testCase) {
        return Optional.ofNullable(screenShotService.get(testCase))
                .map(screenShotMapper::toScreenshotSize)
                .orElse(null);
    }

    public List<Image> getImages(String testCase) {
        log.debug("Получаем изображения из БД по имени тест-кейса: {}", testCase);
        return testCaseService.get(testCase).getImages().stream()
                .map(imageEntity -> Image.builder()
                        .image(imageEntity.getPhotoExpected())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public void delete(String testCase) {
        testCaseService.delete(testCase);
    }
}
