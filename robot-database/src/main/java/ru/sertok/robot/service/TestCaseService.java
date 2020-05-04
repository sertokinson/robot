package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional("transactionManager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final ScreenShotRepository screenShotRepository;
    private final MouseRepository mouseRepository;
    private final KeyboardRepository keyboardRepository;
    private final ImageRepository imageRepository;


    public TestCaseEntity get(String name) {
        return testCaseRepository.findByName(name).orElse(null);
    }

    public List<String> getAll() {
        return testCaseRepository
                .findAll()
                .stream()
                .map(TestCaseEntity::getName)
                .collect(Collectors.toList());
    }

    public void delete(String name) {
        TestCaseEntity testCaseEntity = testCaseRepository.findByName(name).get();
        if (testCaseEntity.getImages() != null && !testCaseEntity.getImages().isEmpty()) {
            imageRepository.deleteAll();
        }
        if (testCaseEntity.getMouse() != null && !testCaseEntity.getMouse().isEmpty()) {
            mouseRepository.deleteAll();
        }
        if (testCaseEntity.getKeyboard() != null && !testCaseEntity.getKeyboard().isEmpty()) {
            keyboardRepository.deleteAll();
        }
        if (testCaseEntity.getScreenShots() != null && !testCaseEntity.getScreenShots().isEmpty()) {
            screenShotRepository.deleteAll();
        }
        testCaseRepository.deleteByName(name);
    }

    public void save(TestCaseEntity testCaseEntity) {
        testCaseRepository.save(testCaseEntity);
    }
}
