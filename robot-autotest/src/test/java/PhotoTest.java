import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PhotoTest {
    @Test
    public void test() throws IOException {
        System.out.println(compare());
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

    private boolean compare() throws IOException {
        BufferedImage image2 = loadPNG("/Users/sergejstahanov/Desktop/robot/robot-autotest/src/test/resources/test1.png");
        BufferedImage image1 = loadPNG("/Users/sergejstahanov/Desktop/robot/robot-autotest/src/test/resources/test2.png");

        if (image1.getWidth() != image2.getWidth() || image2.getHeight() != image1.getHeight()) {
            System.out.println("dimensions must be the same");
            return false;
        }
        int countIsNotIdentic = 0;
        for (int i = 0; i < image2.getWidth(); i++) {
            for (int j = 0; j < image2.getHeight(); j++) {
                int actualRGB = image2.getRGB(i, j);
                int expectedRGB = image1.getRGB(i, j);
                Color color1 = new Color((actualRGB >> 16) & 0xFF, (actualRGB >> 8) & 0xFF, (actualRGB) & 0xFF);
                Color color2 = new Color((expectedRGB >> 16) & 0xFF, (expectedRGB >> 8) & 0xFF, (expectedRGB) & 0xFF);
                if (!compareColor(color1, color2)) {
                    countIsNotIdentic++;
                }
            }
        }
        return (countIsNotIdentic * 100) / (image2.getWidth() * image2.getHeight()) <= 10;
    }

    // reading picture from disk
    private BufferedImage loadPNG(String filename) throws IOException {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;

    }
}
