package ru.sertok.robot.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScreenShotRequest {
    private int x;
    private int y;
    private int width;
    private int height;
}
