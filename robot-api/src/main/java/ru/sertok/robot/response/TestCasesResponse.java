package ru.sertok.robot.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.TestCase;

import java.util.List;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TestCasesResponse extends BaseResponse{
    private List<TestCase> testCases;
}
