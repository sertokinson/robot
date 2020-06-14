package ru.sertok.robot.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.sertok.robot.data.ScreenshotSize;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenShotRequest {
   /**
    * Размеры скриншота
    */
   private ScreenshotSize size;
}
