package ru.sertok.robot.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.database.Database;

import java.awt.*;
import java.awt.image.BufferedImage;

@Component
public class ScreenShotImpl implements ScreenShot {
    @Autowired
    private Database database;

    private Dimension dimension;
    private Point point;
    private boolean stop = false;
    private boolean start = false;
    private Integer count = 0;

    @Override
    public void make(String testCase) {
        start = true;
        if (dimension.width > 0 && dimension.height > 0) {
            new Thread(() -> {
                while (!stop) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    database.writePng(grabScreen(), testCase, count.toString());
                    count++;
                }
            }).start();
        }
    }

    @Override
    public void setSize(Point point, Dimension dimension) {
            this.point = point;
            this.dimension = dimension;
    }

    @Override
    public void stop() {
        stop = true;
        start = false;
    }

    @Override
    public boolean isStarted() {
        return start;
    }

    private BufferedImage grabScreen() {
        try {
            return new Robot().createScreenCapture(new Rectangle(point, dimension));
        } catch (SecurityException | AWTException e) {
            e.printStackTrace();
        }
        return null;
    }

}
