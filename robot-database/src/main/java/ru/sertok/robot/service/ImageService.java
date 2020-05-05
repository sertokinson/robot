package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.repository.ImageRepository;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageEntity get(int position) {
        log.debug("Получаем изображение из БД по позиции: {}", position);
        return imageRepository.findByPosition(position).orElse(null);
    }

    public void save(ImageEntity imageEntity) {
        log.debug("Сохраняем изображение в БД {}", imageEntity);
        imageRepository.save(imageEntity);
    }

    void deleteAll() {
        log.debug("Удаляем все изображения из бд");
        imageRepository.deleteAll();
    }
}
