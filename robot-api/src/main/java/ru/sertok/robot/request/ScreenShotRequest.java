package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScreenShotRequest {
    @Parameter(required = true)
    private int x;
    @Parameter(required = true)
    private int y;
    @Parameter(required = true)
    private int width;
    @Parameter(required = true)
    private int height;
}
