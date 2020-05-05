package ru.sertok.robot.core.hook;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

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
            case "Left Meta":
            case "Right Meta":
                newKey = "Meta";
                break;
            case "Left Control":
            case "Right Control":
                newKey = "Control";
                break;
            default:
                newKey = key;
        }
        for (Field field : KeyEvent.class.getFields()) {
            String keyboard = field.getName().replace("VK_", "");
            if (keyboard.equals(newKey.toUpperCase())) {
                return (int) field.get(field.getName());
            }
        }
        return 0;
    }
}
