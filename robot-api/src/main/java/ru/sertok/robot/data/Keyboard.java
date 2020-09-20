package ru.sertok.robot.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Keyboard extends BaseData {
    private String key;

    public Keyboard(String key) {
        super(Type.KEYBOARD);
        this.key = key;
    }
}
