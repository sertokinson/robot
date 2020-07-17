package ru.sertok.robot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String photoExpected;
    private String photoActual;
    private Boolean result;
}
