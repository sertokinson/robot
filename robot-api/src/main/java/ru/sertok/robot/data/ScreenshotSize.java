package ru.sertok.robot.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ScreenshotSize {
    private int x;
    private int y;
    private int width;
    private int height;
}
