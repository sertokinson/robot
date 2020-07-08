package ru.sertok.robot.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.Result;

import java.util.List;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ResultResponse extends BaseResponse {
    private List<Result> results;
}
