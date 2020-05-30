package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.data.enumerate.TypePressed;

@Getter
@SuperBuilder
public class Mouse extends BaseData{
    private Type type;
    private TypePressed typePressed;
    private int x;
    private int y;
    private int wheel;
}
