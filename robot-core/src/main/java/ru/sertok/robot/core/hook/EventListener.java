package ru.sertok.robot.core.hook;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.core.service.ScreenShot;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.data.enumerate.TypePressed;
import ru.sertok.robot.storage.LocalStorage;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {
    private final LocalStorage localStorage;
    private final ScreenShot screenShot;
    private final KeyEvents keyEvents;

    private long currentTime;
    private long screenShotTime;

    @PostConstruct
    public void init() {
        currentTime = System.currentTimeMillis();
    }

    @Override
    @SneakyThrows
    public void nativeKeyPressed(NativeKeyEvent e) {
        key(Type.PRESSED, e);
    }

    @Override
    @SneakyThrows
    public void nativeKeyReleased(NativeKeyEvent e) {
        key(Type.RELEASED, e);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        click(Type.PRESSED, e);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        click(Type.RELEASED, e);
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (getCurrentTime() > 0)
            localStorage.getSteps().add(getMouse(e));
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        if (!localStorage.isActiveCrop() || localStorage.isScreenshotStart())
            localStorage.getSteps().add(getMouse(e));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        if (!localStorage.isActiveCrop() || localStorage.isScreenshotStart())
            localStorage.getSteps().add(Mouse.builder()
                    .type(Type.WHEEL)
                    .wheel(e.getWheelRotation())
                    .time(getTime())
                    .screenshot(makeScreenshot())
                    .build());
    }

    private void key(Type type, NativeKeyEvent e) throws IllegalAccessException {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        System.out.println(keyText);
        System.out.println(keyEvents.getKey(keyText));
        if (keyEvents.getKey(keyText) != 0)
            localStorage.getSteps().add(getKeyboard(keyText, type));
    }

    private void click(Type type, NativeMouseEvent e) {
        if (!localStorage.isActiveCrop() || localStorage.isScreenshotStart())
            localStorage.getSteps().add(Mouse.builder()
                    .x(e.getX())
                    .y(e.getY())
                    .typePressed(TypePressed.getType(e.getButton()))
                    .count(e.getClickCount())
                    .type(type)
                    .time(getTime())
                    .screenshot(makeScreenshot())
                    .build());
    }

    private boolean makeScreenshot() {
        if (localStorage.isScreenshotStart() && getScreenShotTime() > 200) {
            screenShotTime = System.currentTimeMillis();
            List<Image> images = localStorage.getImages();
            Image image1 = images.get(images.size() - 1);
            return Optional.ofNullable(screenShot.make())
                    .map(image2 -> screenShot.compare(image1.getImage(), image2))
                    .orElse(false);
        }
        return false;
    }

    private int getTime() {
        currentTime = System.currentTimeMillis();
        return (int) (System.currentTimeMillis() - localStorage.getStartTime());
    }

    private int getCurrentTime() {
        return (int) (System.currentTimeMillis() - currentTime);
    }

    private long getScreenShotTime() {
        return currentTime - screenShotTime;
    }

    private Keyboard getKeyboard(String key, Type type) {
        return Keyboard.builder()
                .type(type)
                .time(getTime())
                .key(key)
                .screenshot(makeScreenshot())
                .build();
    }

    private Mouse getMouse(NativeMouseEvent e) {
        return Mouse.builder()
                .x(e.getX())
                .y(e.getY())
                .type(Type.MOVED)
                .time(getTime())
                .screenshot(makeScreenshot())
                .build();
    }
}