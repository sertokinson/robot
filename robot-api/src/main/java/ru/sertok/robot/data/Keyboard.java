package ru.sertok.robot.data;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Keyboard extends BaseData {
    private Type type;
    private String key;
}
