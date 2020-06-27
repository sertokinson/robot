package ru.sertok.robot.core.hook;

import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

@Component
public class KeyEvents {
    public int getKey(String key) throws IllegalAccessException {
        String newKey;
        switch (key) {
            case "⏎":
                newKey = "Enter";
                break;
            case "␣":
                newKey = "Space";
                break;
            case "Left Control":
            case "Right Control":
                newKey = "Control";
                break;
            case "Backspace":
                newKey = "BACK_SPACE";
                break;
            case "Left Alt":
                newKey = "Alt";
                break;
            default:
                newKey = key;
        }
        for (Field field : KeyEvent.class.getFields()) {
            String keyboard = field.getName().replace("VK_", "");
            if (keyboard.equalsIgnoreCase(newKey)) {
                return (int) field.get(field.getName());
            }
        }
        return 0;
    }
}
