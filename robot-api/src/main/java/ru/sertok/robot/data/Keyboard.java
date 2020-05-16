package ru.sertok.robot.data;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.Type;

@Getter
@SuperBuilder
public class Keyboard extends BaseData {
    private Type type;
    private String key;
}
