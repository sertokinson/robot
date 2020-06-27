package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.entity.ScreenShotEntity;
import ru.sertok.robot.repository.ScreenShotRepository;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShotService {
    private final ScreenShotRepository screenShotRepository;

    public void save(ScreenShotEntity screenShotEntity) {
        log.debug("Сохраняем размеры скриншота в бд: {}", screenShotEntity);
        screenShotRepository.save(screenShotEntity);
    }

    public ScreenShotEntity get(String testCaseName) {
        log.debug("Получаем размеры скриншота по имени теста: {}", testCaseName);
        return screenShotRepository.findByTestCase_Name(testCaseName).orElse(null);
    }
}
