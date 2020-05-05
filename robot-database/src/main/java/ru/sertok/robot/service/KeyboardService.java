package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.entity.KeyboardEntity;
import ru.sertok.robot.repository.KeyboardRepository;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;

    public void save(KeyboardEntity keyboardEntity) {
        log.debug("Сохраняем событе клавиатуры в БД: {}", keyboardEntity);
        keyboardRepository.save(keyboardEntity);
    }

    void deleteAll() {
        log.debug("Удаляем все события клавиатуры из бд");
        keyboardRepository.deleteAll();
    }
}
