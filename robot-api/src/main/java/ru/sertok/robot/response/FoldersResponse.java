package ru.sertok.robot.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.TestCase;

import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FoldersResponse extends BaseResponse {
    private Map<String, List<TestCase>> folders;
}
