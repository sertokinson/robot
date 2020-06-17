package ru.sertok.robot.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.TestStatus;

@Getter
@SuperBuilder
public class RobotResponse extends BaseResponse {
    private TestStatus testStatus;
}
