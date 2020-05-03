package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.KeyboardEntity;

public interface KeyboardRepository extends JpaRepository<KeyboardEntity, Long> {
}
