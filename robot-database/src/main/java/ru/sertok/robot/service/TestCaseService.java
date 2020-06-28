package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.entity.BrowserEntity;
import ru.sertok.robot.entity.DesktopEntity;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.mapper.TestCaseMapper;
import ru.sertok.robot.repository.FolderRepository;
import ru.sertok.robot.repository.TestCaseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final TestCaseMapper testCaseMapper;
    private final SettingsService settingsService;
    private final FolderRepository folderRepository;

    public TestCase getTestCase(String name) {
        return testCaseRepository.findByName(name)
                .map(this::get)
                .orElse(null);
    }

    public TestCaseEntity getTestCaseEntity(String name) {
        return testCaseRepository.findByName(name).orElse(null);
    }

    public TestCase get(TestCaseEntity testCaseEntity) {
        TestCase testCase = testCaseMapper.toTestCase(testCaseEntity);
        testCase.setFolderName(settingsService.getFolder(testCaseEntity.getFolderId()).getName());
        if (testCase.getIsBrowser()) {
            BrowserEntity browser = settingsService.getBrowser(testCaseEntity.getBrowserId());
            testCase.setAppName(browser.getName());
            testCase.setPathToApp(browser.getPath());
            testCase.setUrl(settingsService.getUrl(testCaseEntity.getUrlId()).getUrl());
        } else {
            DesktopEntity desktop = settingsService.getDesktop(testCaseEntity.getDesktopId());
            testCase.setAppName(desktop.getName());
            testCase.setPathToApp(desktop.getPath());
        }
        return testCase;
    }

    public List<TestCase> getAll() {
        return testCaseRepository
                .findAll()
                .stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    public Map<String, List<TestCase>> getFolders() {
        Map<String, List<TestCase>> result = new HashMap<>();
        folderRepository.findAll()
                .forEach(folderEntity ->
                        result.put(
                                folderEntity.getName(),
                                testCaseRepository
                                        .findAllByFolderId(folderEntity.getId())
                                        .stream()
                                        .map(this::get)
                                        .collect(Collectors.toList())
                        ));
        return result;
    }

    public void delete(TestCaseEntity testCaseEntity) {
        log.debug("Удаляем тест кейс: {}", testCaseEntity.getName());
        testCaseRepository.delete(testCaseEntity);
    }

    public void delete(String testCase) {
        log.debug("Удаляем тест кейс: {}", testCase);
        testCaseRepository.deleteByName(testCase);
    }

    public void save(TestCaseEntity testCaseEntity) {
        testCaseRepository.save(testCaseEntity);
    }
}
