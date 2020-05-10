package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.entity.MouseEntity;
import ru.sertok.robot.repository.MouseRepository;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MouseService {
    private final MouseRepository mouseRepository;

    public void save(MouseEntity mouseEntity) {
        log.debug("Сохраняем событие мыши в БД: {}", mouseEntity);
        mouseRepository.save(mouseEntity);
    }
}
