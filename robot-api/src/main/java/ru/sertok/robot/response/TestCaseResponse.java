package ru.sertok.robot.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.TestCase;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TestCaseResponse extends BaseResponse {
    private TestCase testCase;
}
