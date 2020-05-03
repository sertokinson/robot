package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sertok.robot.entity.TestCaseEntity;

import java.util.Optional;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCaseEntity, Long> {
    Optional<TestCaseEntity> findByName(String name);
    void deleteByName(String name);
}
