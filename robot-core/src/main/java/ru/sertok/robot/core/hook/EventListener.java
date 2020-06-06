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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.sertok.robot.core.ScreenShot;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.data.enumerate.TypePressed;
import ru.sertok.robot.gui.ScreenShotButtons;
import ru.sertok.robot.gui.TranslucentWindow;
import ru.sertok.robot.storage.LocalStorage;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener, NativeMouseWheelListener {
    private final LocalStorage localStorage;
    private final ScreenShot screenShot;
    private final Environment env;
    private final TranslucentWindow tw;
    private final ScreenShotButtons screenShotButtons;
    private final KeyEvents keyEvents;

    private long currentTime;
    private long screenShotTime;
    private int x = 0;
    private int y = 0;

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
        cropButtons(e);
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
        cropArea(e);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }

    private void key(Type type, NativeKeyEvent e) throws IllegalAccessException {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
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
        boolean screenshot = localStorage.isScreenshotStart();
        if (screenshot && getScreenShotTime() > 200) {
            screenShotTime = System.currentTimeMillis();
            screenShot.make();
            return true;
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


    private void cropButtons(NativeMouseEvent e) {
        if (isActiveCrop(e)) {
            x = e.getX();
            y = e.getY();
            screenShotButtons.setLocation(x - 60, y);
            screenShotButtons.setVisible(true);
            tw.dispose();
        }
    }

    private void cropArea(NativeMouseEvent e) {
        if (isActiveCrop(e)) {
            int width = e.getX() - x;
            int height = e.getY() - y;
            tw.setSize(x, y, e.getX(), e.getY());
            screenShot.setSize(Image.builder()
                    .x(x)
                    .y(y)
                    .width(width)
                    .height(height)
                    .build()
            );
        }
    }

    private boolean isActiveCrop(NativeMouseEvent e) {
        return env.getActiveProfiles().length != 0
                && env.getActiveProfiles()[0].equals("local-gui")
                && !localStorage.isScreenshotStart()
                && localStorage.isActiveCrop()
                && (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50);
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {
        if (!localStorage.isActiveCrop() || localStorage.isScreenshotStart())
            localStorage.getSteps().add(Mouse.builder()
                    .type(Type.WHEEL)
                    .wheel(nativeMouseWheelEvent.getWheelRotation())
                    .time(getTime())
                    .screenshot(makeScreenshot())
                    .build());
    }
}