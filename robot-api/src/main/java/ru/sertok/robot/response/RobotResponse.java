package ru.sertok.robot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RobotResponse {
    private List<String> testCases;
}
