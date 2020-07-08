package ru.sertok.robot.data;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.TypePressed;

@Getter
@ToString
@SuperBuilder
public class Mouse extends BaseData {
    private TypePressed typePressed;
    private int x;
    private int y;
    private int wheel;
    private int count;
}
