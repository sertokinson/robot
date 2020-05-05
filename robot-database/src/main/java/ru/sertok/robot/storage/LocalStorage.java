package ru.sertok.robot.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.TestCase;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LocalStorage {
    private long startTime;
    private List<Object> steps = new ArrayList<>();
    private List<Image> images;
    private boolean screenshot = false;
    private Image image;
    private TestCase testCase;

    public void setStartTime(long startTime) {
        log.debug("Записываем в локальное хранилище время начала {}", startTime);
        this.startTime = startTime;
    }

    public void setSteps(List<Object> steps) {
        this.steps = steps;
    }

    public void setImages(List<Image> images) {
        log.debug("Записываем в локальное хранилище информацию о изображениях: {}", images);
        this.images = images;
    }

    public void setScreenshot(boolean screenshot) {
        log.debug("Активация скриншота: {}", screenshot);
        this.screenshot = screenshot;
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
        return startTime;
    }

    public List<Object> getSteps() {
        log.debug("Вычитываем из локального хранилища данные о шагах: {}", steps);
        return steps;
    }

    public List<Image> getImages() {
        log.debug("Вычитываем из локального хранилища изображения: {}", images);
        return images;
    }

    public boolean isScreenshot() {
        return screenshot;
    }

    public Image getImage() {
        return image;
    }

    public TestCase getTestCase() {
        log.debug("Вычитываем из локального хранилища данные о тест кейсе: {}", testCase);
        return testCase;
    }
}
