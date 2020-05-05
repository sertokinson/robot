package ru.sertok.robot.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShot {
    private final LocalStorage localStorage;

    private Dimension dimension;
    private Point point;
    private boolean start = false;

    public void make() {
        log.debug("Создаем скриншот");
        start = true;
        if (dimension.width > 0 && dimension.height > 0) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bufferedImage = grabScreen();
            if (bufferedImage != null) {
                try {
                    ImageIO.write(bufferedImage, "png", baos);
                    baos.flush();
                } catch (IOException e) {
                    log.error("ошибка при создании скриншота", e);
                }
                localStorage.getImages().add(Image.builder().image(baos.toByteArray()).build());
            }
        } else {
            log.error("Не удалось сделать скриншот, т.к. ширина или высота раны нулю");
        }
    }

    public void setSize(Point point, Dimension dimension) {
        log.debug("Задаем начальную позицию скриншота x: {} y:{}", point.getX(), point.getY());
        this.point = point;
        log.debug("Задаем ширину и высоту скриншота width: {} height:{}", dimension.getWidth(), dimension.getHeight());
        this.dimension = dimension;
    }

    public Image getImage() {
        return Image.builder()
                .x((int) point.getX())
                .y((int) point.getY())
                .width((int) dimension.getWidth())
                .height((int) dimension.getHeight())
                .build();
    }

    public boolean isStarted() {
        return start;
    }

    private BufferedImage grabScreen() {
        try {
            return new Robot().createScreenCapture(new Rectangle(point, dimension));
        } catch (SecurityException | AWTException e) {
            log.error("ошибка при создании скриншота", e);
        }
        return null;
    }

}
