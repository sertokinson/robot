package ru.sertok.robot.listener.hook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.listener.data.Keyboard;
import ru.sertok.robot.listener.data.Mouse;
import ru.sertok.robot.listener.gui.RecordButtons;
import ru.sertok.robot.listener.gui.RecordWindow;
import ru.sertok.robot.listener.gui.TranslucentWindow;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ru.sertok.robot.listener.gui.RecordWindow.activeCrop;


@Component
public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    @Autowired
    private TranslucentWindow tw;
    @Autowired
    private RecordButtons recordButtons;
    @Autowired
    private ScreenShot screenShot;

    private int x = 0;
    private int y = 0;

    public static List<Object> steps = new ArrayList<>();


    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        if (!screenShot.isStarted())
            componentCrop(e);
        steps.add(new Mouse("pressed", e.getX(), e.getY(), (double) (System.currentTimeMillis() - RecordWindow.startTime)));
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        steps.add(new Mouse("released", e.getX(), e.getY(), (double) (System.currentTimeMillis() - RecordWindow.startTime)));
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
        steps.add(new Keyboard("pressed", NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        steps.add(new Keyboard("released", NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        if (activeCrop && !screenShot.isStarted()&& (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50)) {
            int width = e.getX() - x;
            int height = e.getY() - y;
            tw.setSize(x, y, e.getX(), e.getY());
            screenShot.setSize(new Point(x, y), new Dimension(width, height));
        }
    }

    private void componentCrop(NativeMouseEvent e) {
        if (activeCrop && (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50)) {
            x = e.getX();
            y = e.getY();
            recordButtons.setLocation(x - 60, y);
            recordButtons.setVisible(true);
            tw.dispose();
        }
    }

}