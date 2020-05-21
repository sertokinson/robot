package ru.sertok.robot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.sertok.robot.data.BaseTestCase;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestCasesResponse {
    private List<BaseTestCase> testCases;
}
