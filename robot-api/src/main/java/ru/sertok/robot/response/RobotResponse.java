package ru.sertok.robot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.TestStatus;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RobotResponse extends BaseResponse {
    private TestStatus testStatus;
}
