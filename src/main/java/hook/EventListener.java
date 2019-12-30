package hook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    public static List<Object> steps = new ArrayList<>();

    public void nativeMouseClicked(NativeMouseEvent e) {
        // System.out.println("Mouse Clicked: " + e.getX());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("hook.MousePassed: "+e.getX()+" "+e.getY());
        steps.add(new MousePassed(e.getX(), e.getY()));
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        // System.out.println("Mouse Released: " + e.getButton());
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
            System.out.println(new String(NativeKeyEvent.getKeyText(e.getKeyCode()).getBytes(),"UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        steps.add(new KeyboardPassed(NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //  System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //  System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void nativeMouseMoved(NativeMouseEvent var1) {

    }

    public void nativeMouseDragged(NativeMouseEvent var1) {

    }
}