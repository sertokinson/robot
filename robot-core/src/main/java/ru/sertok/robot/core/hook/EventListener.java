package ru.sertok.robot.core.hook;

import lombok.RequiredArgsConstructor;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.core.ScreenShot;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.storage.LocalStorage;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    private final LocalStorage localStorage;
    private final ScreenShot screenShot;
    private long currentTime;
    private long screenShotTime;

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        localStorage.getSteps().add(getKeyboard(e.getKeyCode(), Type.PRESSED));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        localStorage.getSteps().add(getKeyboard(e.getKeyCode(), Type.RELEASED));
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        localStorage.getSteps().add(getMouse(e, Type.PRESSED));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        localStorage.getSteps().add(getMouse(e, Type.RELEASED));

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (getCurrentTime() > 0)
            localStorage.getSteps().add(getMouse(e, Type.MOVED));
    }

    private boolean makeScreenshot() {
        boolean screenshot = localStorage.isScreenshotStart();
        if (screenshot && getScreenShotTime() > 100) {
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

    private int getScreenShotTime() {
        return (int) (System.currentTimeMillis() - screenShotTime);
    }

    private Keyboard getKeyboard(int key, Type type) {
        return Keyboard.builder()
                .type(type)
                .time(getTime())
                .key(NativeKeyEvent.getKeyText(key))
                .screenshot(makeScreenshot())
                .build();
    }

    private Mouse getMouse(NativeMouseEvent e, Type type) {
        return Mouse.builder()
                .x(e.getX())
                .y(e.getY())
                .type(type)
                .time(getTime())
                .screenshot(makeScreenshot())
                .build();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }
}