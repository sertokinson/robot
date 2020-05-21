package ru.sertok.robot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.sertok.robot.data.enumerate.Status;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RobotResponse {
    private Status status;
}
