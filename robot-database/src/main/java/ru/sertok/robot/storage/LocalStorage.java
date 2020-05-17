package ru.sertok.robot.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.TestCase;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LocalStorage {
    private Long startTime;
    private List<BaseData> steps = new ArrayList<>();
    private List<Image> images;
    private boolean screenshotStart = false;
    private Image image;
    private TestCase testCase;
    private boolean activeCrop = false;

    public void setStartTime(long startTime) {
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

    public void setImage(Image image) {
        log.debug("Записываем в локальное хранилище изображение: {}", image);
        this.image = image;
    }

    public void setActiveCrop(boolean activeCrop) {
        log.debug("Записываем в локальное хранилище активацию кропа: {}", activeCrop);
        this.activeCrop = activeCrop;
    }

    public void setTestCase(TestCase testCase) {
        log.debug("Записываем в локальное хранилище данные о тест-кейсе: {}", testCase);
        this.testCase = testCase;
    }

    public long getStartTime() {
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

    public Image getImage() {
        return image;
    }

    public TestCase getTestCase() {
        log.debug("Вычитываем из локального хранилища данные о тест кейсе: {}", testCase);
        return testCase;
    }

    public void invalidateLocalStorage() {
        startTime = null;
        steps = new ArrayList<>();
        images = null;
        screenshotStart = false;
        image = null;
        testCase = null;
        activeCrop = false;
    }
}
