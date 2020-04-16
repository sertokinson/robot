package ru.sertok.robot.api;

import java.awt.*;

public interface ScreenShot {
    void make(String testCase);
    void setSize(Point point, Dimension dimension);
    void stop();
    boolean isStarted();
}
