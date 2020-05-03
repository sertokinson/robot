package ru.sertok.robot.api;

import ru.sertok.robot.data.Image;

import java.awt.*;

public interface ScreenShot {
    void make(String testCase);
    void makeRobot(String testCase);
    void setSize(Point point, Dimension dimension);
    Image getImage();
    void stop();
    boolean isStarted();
}
