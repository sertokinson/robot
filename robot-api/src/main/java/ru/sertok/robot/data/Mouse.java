package ru.sertok.robot.data;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mouse {
    private Type type;
    private int x;
    private int y;
    private Integer time;
}
