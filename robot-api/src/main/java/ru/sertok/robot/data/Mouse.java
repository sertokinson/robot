package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.TypePressed;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Mouse extends BaseData {
    private TypePressed typePressed;
    private int x;
    private int y;
    private int wheel;
    private int count;
}
