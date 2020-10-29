package ru.sertok.robot.core.hook;

import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

@Component
public class KeyEvents {
    public String getKey(String key) {
        String newKey;
        switch (key) {
            case "⏎":
                newKey = "Enter";
                break;
            case "␣":
                newKey = "Space";
                break;
            default:
                newKey = key;
        }
        return newKey;
    }
}
