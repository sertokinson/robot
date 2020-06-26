package ru.sertok.robot.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.ScreenshotSize;
import ru.sertok.robot.data.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Локальное хранилище
 */
@Slf4j
@Component
public class LocalStorage {
    /**
     * Данные по тесту
     */
    private TestCase testCase;

    /**
     * Время начала записи теста
     */
    private Long startTime;

    /**
     * Все шаги пройденные тестом
     */
    private List<BaseData> steps = new ArrayList<>();

    /**
     * Скриншоты которые были сделаны во время теста
     */
    private List<Image> images;

    /**
     * Признак того что начата запись скриншотов
     */
    private boolean screenshotStart = false;

    /**
     * Нажатие на кнопку crop
     */
    private boolean activeCrop = false;

    /**
     * Размеры скриншота
     */
    private ScreenshotSize size;

    /**
     * Сработало предупреждение об наименовании теста
     */
    private boolean warningTestCaseName = false;

    /**
     * Сработало предупреждение об наименовании браузера
     */
    private boolean warningBrowser = false;

    /**
     * Сработало предупреждение об наименовании приложения
     */
    private boolean warningDesktop = false;

    public boolean isWarningDesktop() {
        log.debug("Вычитываем из локального хранилища предупреждение об наименовании приложения: {}", warningDesktop);
        return warningDesktop;
    }

    public void setWarningDesktop(boolean warningDesktop) {
        log.debug("Записываем в локальное хранилище предупреждение об наименовании приложения {}", warningDesktop);
        this.warningDesktop = warningDesktop;
    }

    public boolean isWarningBrowser() {
        log.debug("Вычитываем из локального хранилища предупреждение об наименовании браузера: {}", warningBrowser);
        return warningBrowser;
    }

    public void setWarningBrowser(boolean warningBrowser) {
        log.debug("Записываем в локальное хранилище предупреждение об наименовании браузера {}", warningBrowser);
        this.warningBrowser = warningBrowser;
    }

    public boolean isWarningTestCaseName() {
        log.debug("Вычитываем из локального хранилища предупреждение об наименовании теста: {}", warningTestCaseName);
        return warningTestCaseName;
    }

    public void setWarningTestCaseName(boolean warningTestCaseName) {
        log.debug("Записываем в локальное хранилище предупреждение об наименовании теста {}", warningTestCaseName);
        this.warningTestCaseName = warningTestCaseName;
    }

    public ScreenshotSize getSize() {
        log.debug("Вычитываем из локального хранилища размеры скриншота: {}", size);
        return size;
    }

    public void setSize(ScreenshotSize size) {
        log.debug("Записываем в локальное хранилище размеры скриншота {}", size);
        this.size = size;
    }

    public void setStartTime(Long startTime) {
        log.debug("Записываем в локальное хранилище время начала {}", startTime);
        this.startTime = startTime;
    }

    public void setImages(List<Image> images) {
        log.debug("Записываем в локальное хранилище информацию о изображениях: {}", images);
        this.images = images;
    }

    public void setScreenshotStart(boolean start) {
        log.debug("Активация скриншота: {}", start);
        this.screenshotStart = start;
    }

    public void setActiveCrop(boolean activeCrop) {
        log.debug("Записываем в локальное хранилище активацию кропа: {}", activeCrop);
        this.activeCrop = activeCrop;
    }

    public void setTestCase(TestCase testCase) {
        log.debug("Записываем в локальное хранилище данные о тест-кейсе: {}", testCase);
        this.testCase = testCase;
    }

    public Long getStartTime() {
        log.debug("Вычитываем из локального хранилища время начала: {}", startTime);
        return startTime;
    }

    public List<BaseData> getSteps() {
        log.debug("Вычитываем из локального хранилища данные о шагах: {}", steps);
        return steps;
    }

    public List<Image> getImages() {
        log.debug("Вычитываем из локального хранилища изображения: {}", images);
        return images;
    }

    public boolean isScreenshotStart() {
        return screenshotStart;
    }

    public boolean isActiveCrop() {
        return activeCrop;
    }


    public TestCase getTestCase() {
        log.debug("Вычитываем из локального хранилища данные о тест кейсе: {}", testCase);
        return testCase;
    }

    public void invalidateLocalStorage() {
        warningTestCaseName = false;
        warningBrowser = false;
        warningDesktop = false;
        startTime = null;
        steps = new ArrayList<>();
        images = null;
        screenshotStart = false;
        size = null;
        testCase = null;
        activeCrop = false;
    }
}
