package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.repository.ImageRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {
    private final ImageRepository imageRepository;

    public List<ImageEntity> getAll(TestCaseEntity testCaseEntity) {
        log.debug("Получаем все изображение из БД по тесту: {}", testCaseEntity.getName());
        return imageRepository.findAllByTestCase(testCaseEntity).orElse(null);
    }

    public void save(ImageEntity imageEntity) {
        log.debug("Сохраняем изображение в БД {}", imageEntity);
        imageRepository.save(imageEntity);
    }
}
