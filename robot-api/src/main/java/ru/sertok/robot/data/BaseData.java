package ru.sertok.robot.data;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseData {
    private Integer time;
    private boolean screenshot;
}
