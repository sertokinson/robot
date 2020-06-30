package ru.sertok.robot.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.ScreenshotSize;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
    public void makeAsync() {
        log.debug("Создаем скриншот асинхронно");
        if (dimension.width > 0 && dimension.height > 0) {
            Optional.ofNullable(grabScreen()).ifPresent(image ->
                    localStorage.getImages().add(Image.builder().image(resizePhoto(image)).build()));
        } else {
            log.error("Не удалось сделать скриншот, т.к. ширина или высота равны нулю");
        }
    }

    public byte[] make() {
        log.debug("Создаем скриншот синхронно");
        if (dimension.width > 0 && dimension.height > 0) {
            return Optional.ofNullable(grabScreen()).map(image -> {
                byte[] bytes = resizePhoto(image);
                localStorage.getImages().add(Image.builder().image(bytes).build());
                return bytes;
            }).orElse(null);
        } else {
            log.error("Не удалось сделать скриншот, т.к. ширина или высота равны нулю");
            return null;
        }
    }

    public void setSize(ScreenshotSize size) {
        localStorage.setSize(size);
        localStorage.setImages(new ArrayList<>());
        this.point = new Point(size.getX(), size.getY());
        log.debug("Задаем начальную позицию скриншота x: {} y:{}", point.getX() + 1, point.getY() + 1);
        this.dimension = new Dimension(size.getWidth(), size.getHeight());
        log.debug("Задаем ширину и высоту скриншота width: {} height:{}", dimension.getWidth() - 2, dimension.getHeight() - 2);
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

    public boolean compare(byte[] actual, byte[] expected) {
        try {
            BufferedImage expectedImage = ImageIO.read(new ByteArrayInputStream(expected));
            BufferedImage actualImage = ImageIO.read(new ByteArrayInputStream(actual));
            if (actualImage.getWidth() != expectedImage.getWidth() || expectedImage.getHeight() != actualImage.getHeight()) {
                log.error("Размеры фактического изображения width: {} height: {} не совпадают с размерами ожидаемого изображения width: {} height: {}",
                        actualImage.getWidth(), actualImage.getHeight(), expectedImage.getWidth(), expectedImage.getHeight());
                return false;
            }
            // количество не идентичных пикселей
            int countIsNotIdentic = 0;
            for (int i = 0; i < actualImage.getWidth(); i++) {
                for (int j = 0; j < actualImage.getHeight(); j++) {
                    int actualRGB = actualImage.getRGB(i, j);
                    int expectedRGB = expectedImage.getRGB(i, j);
                    Color color1 = new Color((actualRGB >> 16) & 0xFF, (actualRGB >> 8) & 0xFF, (actualRGB) & 0xFF);
                    Color color2 = new Color((expectedRGB >> 16) & 0xFF, (expectedRGB >> 8) & 0xFF, (expectedRGB) & 0xFF);
                    if (!compareColor(color1, color2)) {
                        countIsNotIdentic++;
                    }
                }
            }
            // допускаем 2% не совпадение
            return (countIsNotIdentic * 100) / (actualImage.getWidth() * actualImage.getHeight()) <= 2;

        } catch (IOException e) {
            log.error("Не смог преобразовать изображение", e);
            return false;
        }
    }

    private boolean compareColor(Color color1, Color color2) {
        if (Math.abs(color1.getRed() - color2.getRed()) > 20) {
            return false;
        }
        if (Math.abs(color1.getBlue() - color2.getBlue()) > 20) {
            return false;
        }
        return Math.abs(color1.getGreen() - color2.getGreen()) <= 20;

    }

}
