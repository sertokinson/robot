package ru.sertok.robot.data;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseData {
    private int time;
    private boolean screenshot;
}
