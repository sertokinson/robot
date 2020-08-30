package ru.sertok.robot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.data.enumerate.TypeAction;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseData {
    private TypeAction typeAction;
    private Type type;
    private int time;
}
