package ru.sertok.robot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.sertok.robot.data.enumerate.TestStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RobotResponse {
    private TestStatus status;
}
