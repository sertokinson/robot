package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sertok.robot.entity.ScreenShotEntity;

@Repository
public interface ScreenShotRepository extends JpaRepository<ScreenShotEntity, Long> {
}
