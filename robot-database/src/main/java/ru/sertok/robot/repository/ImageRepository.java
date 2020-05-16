package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.entity.TestCaseEntity;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByPositionAndTestCase(int position, TestCaseEntity testCaseEntity);
}
