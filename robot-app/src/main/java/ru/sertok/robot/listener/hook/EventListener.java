package ru.sertok.robot.listener.hook;

import lombok.RequiredArgsConstructor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.Type;
import ru.sertok.robot.listener.gui.RecordButtons;
import ru.sertok.robot.listener.gui.TranslucentWindow;
import ru.sertok.robot.storage.LocalStorage;

import java.awt.*;
import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    private final TranslucentWindow tw;
    private final RecordButtons recordButtons;
    private final ScreenShot screenShot;
    private final LocalStorage localStorage;

    private int x = 0;
    private int y = 0;

    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        if (!screenShot.isStarted())
            componentCrop(e);
        if (isListenedZone(e.getX(), e.getY()))
            localStorage.getSteps().add(
                    new Mouse(Type.PRESSED, e.getX(), e.getY(),
                            (int) (System.currentTimeMillis() - localStorage.getStartTime()))
            );
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        if (isListenedZone(e.getX(), e.getY()))
            localStorage.getSteps().add(new Mouse(
                    Type.RELEASED,
                    e.getX(),
                    e.getY(),
                    (int) (System.currentTimeMillis() - localStorage.getStartTime()))
            );
    }


    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(new String(NativeKeyEvent.getKeyText(e.getKeyCode()).getBytes(), StandardCharsets.UTF_8));
        localStorage.getSteps().add(new Keyboard(Type.PRESSED, NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        localStorage.getSteps().add(new Keyboard(Type.RELEASED, NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        localStorage.getSteps().add(Mouse.builder()
                .x(e.getX())
                .y(e.getY())
                .time((int) (System.currentTimeMillis() - localStorage.getStartTime()))
                .type(Type.MOVED)
                .build()
        );
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        if (localStorage.isActiveCrop() && !screenShot.isStarted() && (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50)) {
            int width = e.getX() - x;
            int height = e.getY() - y;
            tw.setSize(x, y, e.getX(), e.getY());
            screenShot.setSize(new Point(x, y), new Dimension(width, height));
        }
    }

    private void componentCrop(NativeMouseEvent e) {
        if (localStorage.isActiveCrop() && (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50)) {
            x = e.getX();
            y = e.getY();
            recordButtons.setLocation(x - 60, y);
            recordButtons.setVisible(true);
            tw.dispose();
        }
    }

    private boolean isListenedZone(int x, int y) {
        Point location = localStorage.getLocation();
        Dimension size = localStorage.getSize();
        return x > location.getX() + size.getWidth() && y > location.getY() + size.getHeight();
    }

}