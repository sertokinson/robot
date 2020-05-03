package ru.sertok.robot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssertData {
    private String expected;
    private String actual;
}
