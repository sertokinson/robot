package ru.sertok.robot.data.enumerate;

public enum BrowserName {
    FIREFOX("Firefox"),
    CHROME("Chrome"),
    SAFARI("Safari"),
    UNKNOWN("Unknown");

    private String name;

    BrowserName(String name) {
        this.name = name;
    }
}
