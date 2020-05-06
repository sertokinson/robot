package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Mouse extends BaseData{
    private Type type;
    private int x;
    private int y;
}
