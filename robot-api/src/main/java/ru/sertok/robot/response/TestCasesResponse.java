package ru.sertok.robot.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.TestCase;

import java.util.List;

@Getter
@SuperBuilder
public class TestCasesResponse extends BaseResponse{
    private List<TestCase> testCases;
}
