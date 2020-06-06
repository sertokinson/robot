package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.api.ImageOutputController;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.request.RobotRequest;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageOutputControllerImpl implements ImageOutputController {
    private final TestCaseService testCaseService;
    private String testCase;

    @Override
    public Response getAll(RobotRequest robotRequest) {
        String path = System.getProperty("java.io.tmpdir") + "images";
        deletefile(new File(path));
        testCase = robotRequest.getTestCase();
        log.debug("Выгружаем изображения по тест-кейсу: {}", testCase);
        List<ImageEntity> images = testCaseService.get(testCase).getImages();
        if(CollectionUtils.isEmpty(images))
            return ResponseBuilder.error("Нет изображений");
        output(path, images);
        return ResponseBuilder.ok(path);
    }

    @Override
    public Response getErrors(RobotRequest robotRequest) {
        String path = System.getProperty("java.io.tmpdir") + "errorImages";
        deletefile(new File(path));
        testCase = robotRequest.getTestCase();
        log.debug("Выгружаем ошибочные изображения по тест-кейсу: {}", testCase);
        List<ImageEntity> images = testCaseService.get(testCase).getImages().stream()
                .filter(imageEntity -> imageEntity.getAssertResult() != null && !imageEntity.getAssertResult())
                .collect(Collectors.toList());
        if (images.isEmpty()) {
            return ResponseBuilder.ok("Нет ошибочных изображений!");
        }
        output(path, images);
        return ResponseBuilder.ok(path);
    }

    private void output(String path, List<ImageEntity> images) {
        for (int i = 0; i < images.size(); i++) {
            byte[] photoExpected = images.get(i).getPhotoExpected();
            if (photoExpected != null) {
                InputStream in = new ByteArrayInputStream(photoExpected);
                try {
                    Integer percent = images.get(i).getPercent();
                    writePng(path, ImageIO.read(in), "recorder", testCase + i + "(" + (100 - (percent == null ? 0 : percent)) + "%" + ")");
                } catch (IOException e) {
                    log.error("Ошибка при выгрузке изображения", e);
                }
            }
        }
        for (int i = 0; i < images.size(); i++) {
            byte[] photoActual = images.get(i).getPhotoActual();
            if (photoActual != null) {
                InputStream in = new ByteArrayInputStream(photoActual);
                try {
                    writePng(path, ImageIO.read(in), "robot", testCase + i + "(" + (100 - images.get(i).getPercent()) + "%" + ")");
                } catch (IOException e) {
                    log.error("Ошибка при выгрузке изображения", e);
                }
            }
        }
    }

    private void deletefile(File path) {
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) deletefile(f);
                else f.delete();
            }
        }
        path.delete();
    }

    private void writePng(String pathName, BufferedImage image, String folder, String filename) {
        Path path = Paths.get(pathName);
        if (!Files.exists(path)) {
            new File(pathName).mkdir();
        }
        new File(pathName + "/" + folder.split("/")[0]).mkdir();
        new File(pathName + "/" + folder).mkdir();

        try {
            ImageIO.write(image, "png", new File(pathName + "/" + folder, filename + ".png"));
        } catch (IOException e) {
            log.error("Ошибка при выгрузке изображения", e);
        }
    }
}
