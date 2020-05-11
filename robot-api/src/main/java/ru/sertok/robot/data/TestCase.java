package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@ToString
@Getter
@SuperBuilder
public class TestCase extends BaseTestCase {
    private List<BaseData> steps;
    private Image image;
}
