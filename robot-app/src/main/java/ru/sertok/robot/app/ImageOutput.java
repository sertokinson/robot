package ru.sertok.robot.app;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;


public class ImageOutput {
    // раскоментирую если понадобится
    /*private void localImageOut() {
        TestCaseEntity testCaseEntity = testCaseRepository.findByName("test").get();
        List<ImageEntity> images = testCaseEntity.getImages();
        images.sort(Comparator.comparingInt(ImageEntity::getPosition));
        for (int i = 0; i < images.size(); i++) {
            InputStream in = new ByteArrayInputStream(images.get(i).getPhotoExpected());
            try {
                database.writePng(ImageIO.read(in), "recorder", "test" + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < images.size(); i++) {
            InputStream in = new ByteArrayInputStream(images.get(i).getPhotoActual());
            try {
                database.writePng(ImageIO.read(in), "robot", "test" + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
