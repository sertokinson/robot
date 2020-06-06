package ru.sertok.robot.data;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.enumerate.TestStatus;

@Getter
@SuperBuilder
public class BaseTestCase {
    private String name;
    private String url;
    private String path;
    private Integer time;
    private String os;
    private Browser browser;
    private String description;
    private TestStatus status;
}
