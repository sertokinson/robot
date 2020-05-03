package ru.sertok.robot.data;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TestCase {
    private String name;
    private String url;
    private List<Object> steps;
}
