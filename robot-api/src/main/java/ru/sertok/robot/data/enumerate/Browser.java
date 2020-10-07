package ru.sertok.robot.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Browser {
    CHROME_PORTABLE("Chrome Portable", "chrome/Google Chrome Portable.exe");

    private String name;
    private String path;
}
