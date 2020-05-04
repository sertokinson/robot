package ru.sertok.robot.api;

import ru.sertok.robot.data.Image;

import java.awt.*;

public interface ScreenShot {
    void make();
    void makeRobot();
    void setSize(Point point, Dimension dimension);
    Image getImage();
    boolean isStarted();
}
