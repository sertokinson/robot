package ru.sertok.robot.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShot {
    private final LocalStorage localStorage;

    private Dimension dimension;
    private Point point;

    @Async
    public void make() {
        log.debug("Создаем скриншот");
        if (dimension.width > 0 && dimension.height > 0) {
            Optional.ofNullable(grabScreen()).ifPresent(image ->
                    localStorage.getImages().add(Image.builder().image(resizePhoto(image)).build()));
        } else {
            log.error("Не удалось сделать скриншот, т.к. ширина или высота равны нулю");
        }
    }

    public void setSize(Image image) {
        localStorage.setImage(image);
        localStorage.setImages(new ArrayList<>());
        this.point = new Point(image.getX(), image.getY());
        log.debug("Задаем начальную позицию скриншота x: {} y:{}", point.getX()+1, point.getY()+1);
        this.dimension = new Dimension(image.getWidth(), image.getHeight());
        log.debug("Задаем ширину и высоту скриншота width: {} height:{}", dimension.getWidth()-2, dimension.getHeight()-2);
    }

    private BufferedImage grabScreen() {
        try {
            return new Robot().createScreenCapture(new Rectangle(point, dimension));
        } catch (SecurityException | AWTException e) {
            log.error("ошибка при создании скриншота", e);
        }
        return null;
    }

    private byte[] resizePhoto(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bufferedImage.getHeight() > 100 || bufferedImage.getWidth() > 100)
            try {
                ImageIO.write(Thumbnails.of(bufferedImage).size(100, 100).asBufferedImage(), "png", baos);
                baos.flush();
            } catch (IOException e) {
                log.error("ошибка при создании скриншота", e);
            }
        return baos.toByteArray();
    }

}
