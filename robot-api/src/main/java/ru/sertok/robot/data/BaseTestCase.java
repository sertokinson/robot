package ru.sertok.robot.data;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseTestCase {
    private String name;
    private String url;
    private String path;
    private Integer time;
}
