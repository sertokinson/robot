package ru.sertok.robot.listener.hook;

import lombok.RequiredArgsConstructor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.Type;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.listener.gui.RecordButtons;
import ru.sertok.robot.listener.gui.TranslucentWindow;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    private final TranslucentWindow tw;
    private final RecordButtons recordButtons;
    private final ScreenShot screenShot;
    private final LocalStorage localStorage;

    private int x = 0;
    private int y = 0;

    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    private boolean compare(BufferedImage actual, BufferedImage expected) {

        if (actual.getWidth() != expected.getWidth() || expected.getHeight() != actual.getHeight()) {
            System.out.println("dimensions must be the same");
            return false;
        }

        int countIsNotIdentic = 0;
        for (int i = 0; i < actual.getWidth(); i++) {
            for (int j = 0; j < actual.getHeight(); j++) {
                int actualRGB = actual.getRGB(i, j);
                int expectedRGB = expected.getRGB(i, j);
                Color color1 = new Color((actualRGB >> 16) & 0xFF, (actualRGB >> 8) & 0xFF, (actualRGB) & 0xFF);
                Color color2 = new Color((expectedRGB >> 16) & 0xFF, (expectedRGB >> 8) & 0xFF, (expectedRGB) & 0xFF);
                if (!compareColor(color1, color2)) {
                    countIsNotIdentic++;
                }
            }
        }
        return (countIsNotIdentic * 100) / (actual.getWidth() * actual.getHeight()) <= 10;
    }

    private boolean compareColor(Color color1, Color color2) {
        if (Math.abs(color1.getRed() - color2.getRed()) > 10) {
            return false;
        }
        if (Math.abs(color1.getBlue() - color2.getBlue()) > 10) {
            return false;
        }
        return Math.abs(color1.getGreen() - color2.getGreen()) <= 10;

    }

    public void nativeMousePressed(NativeMouseEvent e) {
        boolean screenshot = localStorage.isScreenshot();
        if (screenshot) {
            screenShot.make();
            /*List<ImageEntity> images = localStorage.getImages();
            if (images.size() > 1) {
                InputStream actualImage = new ByteArrayInputStream(images.get(images.size() - 1).getPhotoExpected());
                InputStream expectedImage = new ByteArrayInputStream(images.get(images.size() - 2).getPhotoExpected());
                try {
                    if (compare(ImageIO.read(actualImage), ImageIO.read(expectedImage))) {
                        localStorage.getImages().remove(images.size() - 1);
                        screenshot = false;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }*/
        }
        if (!screenShot.isStarted())
            componentCrop(e);
        localStorage.getSteps().add(
                new Mouse(Type.PRESSED, e.getX(), e.getY(),
                        (int) (System.currentTimeMillis() - localStorage.getStartTime()), screenshot)
        );

    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        boolean screenshot = localStorage.isScreenshot();
        if (screenshot) {
            screenShot.make();
           /* List<ImageEntity> images = localStorage.getImages();
            if (images.size() > 1) {
                InputStream actualImage = new ByteArrayInputStream(images.get(images.size() - 1).getPhotoExpected());
                InputStream expectedImage = new ByteArrayInputStream(images.get(images.size() - 2).getPhotoExpected());
                try {
                    if (compare(ImageIO.read(actualImage), ImageIO.read(expectedImage))) {
                        localStorage.getImages().remove(images.size() - 1);
                        screenshot = false;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }*/
        }
        localStorage.getSteps().add(
                new Mouse(Type.RELEASED, e.getX(), e.getY(),
                        (int) (System.currentTimeMillis() - localStorage.getStartTime()), screenshot)
        );
    }


    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(new String(NativeKeyEvent.getKeyText(e.getKeyCode()).getBytes(), StandardCharsets.UTF_8));
        localStorage.getSteps().add(new Keyboard(Type.PRESSED, NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        localStorage.getSteps().add(new Keyboard(Type.RELEASED, NativeKeyEvent.getKeyText(e.getKeyCode())));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        boolean screenshot = localStorage.isScreenshot();
        if (screenshot) {
            screenShot.make();
           /* List<ImageEntity> images = localStorage.getImages();
            if (images.size() > 1) {
                InputStream actualImage = new ByteArrayInputStream(images.get(images.size() - 1).getPhotoExpected());
                InputStream expectedImage = new ByteArrayInputStream(images.get(images.size() - 2).getPhotoExpected());
                try {
                    if (compare(ImageIO.read(actualImage), ImageIO.read(expectedImage))) {
                        localStorage.getImages().remove(images.size() - 1);
                        screenshot = false;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }*/
        }
        localStorage.getSteps().add(
                new Mouse(Type.MOVED, e.getX(), e.getY(),
                        (int) (System.currentTimeMillis() - localStorage.getStartTime()), screenshot)
        );
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        if (localStorage.isActiveCrop() && !screenShot.isStarted() && (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50)) {
            int width = e.getX() - x;
            int height = e.getY() - y;
            tw.setSize(x, y, e.getX(), e.getY());
            screenShot.setSize(new Point(x, y), new Dimension(width, height));
        }
    }

    private void componentCrop(NativeMouseEvent e) {
        if (localStorage.isActiveCrop() && (e.getX() > x || e.getX() < x - 60 || e.getY() < y || e.getY() > y + 50)) {
            x = e.getX();
            y = e.getY();
            recordButtons.setLocation(x - 60, y);
            recordButtons.setVisible(true);
            tw.dispose();
        }
    }

}