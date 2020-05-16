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
    private long startTime;
    private List<BaseData> steps = new ArrayList<>();
    private List<Image> images;
    private boolean screenshotStart = false;
    private Image image;
    private TestCase testCase;

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

    public Image getImage() {
        return image;
    }

    public TestCase getTestCase() {
        log.debug("Вычитываем из локального хранилища данные о тест кейсе: {}", testCase);
        return testCase;
    }
}
