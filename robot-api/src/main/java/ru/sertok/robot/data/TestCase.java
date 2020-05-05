package ru.sertok.robot.data;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private String name;
    private String url;
    private List<Object> steps;
    private Image image;
}
