package ru.sertok.robot.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Result {
    private String photoExpected;
    private String photoActual;
    private Boolean result;
}
