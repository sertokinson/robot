package ru.sertok.robot.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.Status;

@Setter
@Getter
@SuperBuilder
public class BaseResponse {
    private Status status;
    private String error;
}
