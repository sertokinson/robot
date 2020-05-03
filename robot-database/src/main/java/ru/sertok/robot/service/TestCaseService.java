package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.repository.TestCaseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional("transactionManager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;

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
        testCaseRepository.deleteByName(name);
    }

    public void save(TestCaseEntity testCaseEntity) {
        testCaseRepository.save(testCaseEntity);
    }
}
