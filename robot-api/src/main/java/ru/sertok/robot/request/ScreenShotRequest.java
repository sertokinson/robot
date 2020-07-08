package ru.sertok.robot.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.sertok.robot.data.ScreenshotSize;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScreenShotRequest {
    /**
     * Размеры скриншота
     */
    private ScreenshotSize size;
}
