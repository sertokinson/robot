package ru.sertok.robot.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AppResponse extends BaseResponse {
    private String result;
}
