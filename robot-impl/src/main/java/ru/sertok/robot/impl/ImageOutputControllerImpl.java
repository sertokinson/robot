package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.api.ImageOutputController;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.request.ImageOutputRequest;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.service.TestCaseService;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageOutputControllerImpl implements ImageOutputController {
    private final TestCaseService testCaseService;

    @Override
    public Response output(ImageOutputRequest imageOutputRequest) {
        String testCase = imageOutputRequest.getTestCase();
        log.debug("Выгружаем изображения по тест-кейсу: {}", testCase);
        TestCaseEntity testCaseEntity = testCaseService.get("test");
        List<ImageEntity> images = testCaseEntity.getImages();
        images.sort(Comparator.comparingInt(ImageEntity::getPosition));
        for (int i = 0; i < images.size(); i++) {
            InputStream in = new ByteArrayInputStream(images.get(i).getPhotoExpected());
            try {
                writePng(ImageIO.read(in), "recorder", testCase + i);
            } catch (IOException e) {
                log.error("Ошибка при выгрузке изображения", e);
            }
        }
        if (!CollectionUtils.isEmpty(images))
            for (int i = 0; i < images.size(); i++) {
                InputStream in = new ByteArrayInputStream(images.get(i).getPhotoActual());
                try {
                    writePng(ImageIO.read(in), "robot", testCase + i);
                } catch (IOException e) {
                    log.error("Ошибка при выгрузке изображения", e);
                }
            }
        return ResponseBuilder.ok();
    }

    private void writePng(BufferedImage image, String folder, String filename) {
        Path path = Paths.get("database");
        if (!Files.exists(path)) {
            new File("database").mkdir();
        }
        new File("database/" + folder.split("/")[0]).mkdir();
        new File("database/" + folder).mkdir();

        try {
            ImageIO.write(image, "png", new File("database/" + folder, filename + ".png"));
        } catch (IOException e) {
            log.error("Ошибка при выгрузке изображения", e);
        }
    }
}
