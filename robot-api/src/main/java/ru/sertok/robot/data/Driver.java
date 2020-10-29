package ru.sertok.robot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Driver {
    CHROME("chrome"),
    FIREFOX("gecko");

    private final String name;
}
