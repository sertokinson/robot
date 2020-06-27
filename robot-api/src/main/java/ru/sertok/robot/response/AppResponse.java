package ru.sertok.robot.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode
public class AppResponse extends BaseResponse {
    private String result;
}
