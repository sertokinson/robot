import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DemoTest {
    
    @Test
    public void test() throws IOException {
        BufferedImage hand = ImageIO.read(this.getClass().getResourceAsStream("no-red.png"));
        boolean next = false;
        for (int i = 0; i < hand.getWidth(); i++) {
            for (int j = 0; j < hand.getHeight(); j++) {
                int imageRGB = hand.getRGB(i, j);
                if (((imageRGB >> 16) & 0xFF) > 200)
                    next = true;
            }
        }
        System.out.println(next);

    }
}
