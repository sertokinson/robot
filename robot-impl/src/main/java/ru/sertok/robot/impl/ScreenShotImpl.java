package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.entity.ScreenShotEntity;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.repository.TestCaseRepository;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShotImpl implements ScreenShot {
    private final LocalStorage localStorage;

    private Dimension dimension;
    private Point point;
    private boolean start = false;

    @Override
    public void make() {
        start = true;
        if (dimension.width > 0 && dimension.height > 0) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(grabScreen(), "png", baos);
                baos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setPhotoExpected(baos.toByteArray());
            localStorage.getImages().add(imageEntity);
        }
    }

    @Override
    public void makeRobot() {
        if (dimension.width > 0 && dimension.height > 0) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(grabScreen(), "png", baos);
                baos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setPhotoActual(baos.toByteArray());
            localStorage.getImages().add(imageEntity);
        }
    }

    @Override
    public void setSize(Point point, Dimension dimension) {
        this.point = point;
        this.dimension = dimension;
    }

    @Override
    public Image getImage() {
        return Image.builder()
                .x((int) point.getX())
                .y((int) point.getY())
                .width((int) dimension.getWidth())
                .height((int) dimension.getHeight())
                .build();
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
