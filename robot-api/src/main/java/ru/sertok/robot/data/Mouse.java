package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.Type;

@Getter
@SuperBuilder
public class Mouse extends BaseData{
    private Type type;
    private int x;
    private int y;
    private int wheel;
}
