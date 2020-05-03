package ru.sertok.robot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssertResult {
    private String[] messages;
    private int type;
}
