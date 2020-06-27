package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import ru.sertok.robot.api.ResultController;
import ru.sertok.robot.data.Result;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.response.ResultResponse;
import ru.sertok.robot.service.TestCaseService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResultControllerImpl implements ResultController {
    private final TestCaseService testCaseService;

    @Override
    public ResultResponse get(String testCase) {
        log.debug("Выгружаем изображения по тест-кейсу: {}", testCase);
        Base64.Encoder encoder = Base64.getEncoder();
        return ResponseBuilder.success(ResultResponse.builder()
                .results(testCaseService.getTestCaseEntity(testCase).getImages()
                        .stream()
                        .map(imageEntity -> new Result(
                                encoder.encodeToString(imageEntity.getPhotoExpected()),
                                encoder.encodeToString(imageEntity.getPhotoActual()),
                                imageEntity.getAssertResult()
                        ))
                        .collect(Collectors.toList()))
                .build());
    }

    @Override
    public AppResponse toPath(String testCase) {
        String path = System.getProperty("java.io.tmpdir") + "images";
        deleteFile(new File(path));
        log.debug("Выгружаем изображения по тест-кейсу: {}", testCase);
        List<ImageEntity> images = testCaseService.getTestCaseEntity(testCase).getImages();
        if(CollectionUtils.isEmpty(images))
            return ResponseBuilder.error(AppResponse.builder().error("Нет изображений").build());
        output(path, images, testCase);
        return ResponseBuilder.success(AppResponse.builder().result(path).build());
    }

    @Override
    public AppResponse errors(String testCase) {
        String path = System.getProperty("java.io.tmpdir") + "errorImages";
        deleteFile(new File(path));
        log.debug("Выгружаем ошибочные изображения по тест-кейсу: {}", testCase);
        List<ImageEntity> images = testCaseService.getTestCaseEntity(testCase).getImages().stream()
                .filter(imageEntity -> imageEntity.getAssertResult() != null && !imageEntity.getAssertResult())
                .collect(Collectors.toList());
        if (images.isEmpty()) {
            return ResponseBuilder.error(AppResponse.builder().error("Нет ошибочных изображений").build());
        }
        output(path, images, testCase);
        return ResponseBuilder.success(AppResponse.builder().result(path).build());
    }

    private void output(String path, List<ImageEntity> images, String testCase) {
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

    private void deleteFile(File path) {
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) deleteFile(f);
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
