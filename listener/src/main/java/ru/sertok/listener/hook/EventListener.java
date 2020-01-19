package ru.sertok.listener.hook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import ru.sertok.listener.data.Keyboard;
import ru.sertok.listener.data.Mouse;
import ru.sertok.listener.gui.RecordWindow;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    public static List<Object> steps = new ArrayList<>();

    public void nativeMouseClicked(NativeMouseEvent e) {
        // System.out.println("Mouse Clicked: " + e.getX());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("ru.sertok.data.MousePassed: " + e.getX() + " " + e.getY());
        steps.add(new Mouse("pressed",e.getX(), e.getY(), (double) (System.currentTimeMillis() - RecordWindow.startTime)));
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        steps.add(new Mouse("released",e.getX(), e.getY(), (double) (System.currentTimeMillis() - RecordWindow.startTime)));
    }


    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        }
        try {
            System.out.println(new String(NativeKeyEvent.getKeyText(e.getKeyCode()).getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        steps.add(new Keyboard("pressed",NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        steps.add(new Keyboard("released",NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void nativeMouseMoved(NativeMouseEvent var1) {

    }

    public void nativeMouseDragged(NativeMouseEvent var1) {

    }
}