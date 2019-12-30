package hook;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

public class KeyEvents {
    public int getKey(String key) throws IllegalAccessException {
        for (Field field : KeyEvent.class.getFields()) {
            String keyboard = field.getName().replace("VK_", "");
            if (keyboard.equals(key.toUpperCase())) {
                return (int) field.get(field.getName());
            }
        }
        return 0;
    }
}
