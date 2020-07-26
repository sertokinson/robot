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
     * Идентификатор браузера
     */
    private Long browserId;

    /**
     * Идентификатор приложения
     */
    private Long desktopId;

    private Boolean click = false;

    public Boolean getClick() {
        return click;
    }

    public void setClick(Boolean click) {
        this.click = click;
    }

    public Long getBrowserId() {
        log.debug("Вычитываем из локального хранилища браузер: {}", browserId);
        return browserId;
    }

    public void setBrowserId(Long browserId) {
        log.debug("Записываем в локальное хранилище браузер: {}", browserId);
        this.browserId = browserId;
    }

    public Long getDesktopId() {
        log.debug("Вычитываем из локального хранилища приложение: {}", desktopId);
        return desktopId;
    }

    public void setDesktopId(Long desktopId) {
        log.debug("Записываем в локальное хранилище приложение: {}", desktopId);
        this.desktopId = desktopId;
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
        startTime = null;
        steps = new ArrayList<>();
        images = new ArrayList<>();
        screenshotStart = false;
        size = null;
        testCase = null;
        activeCrop = false;
    }
}
