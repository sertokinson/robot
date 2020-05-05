package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sertok.robot.entity.KeyboardEntity;

@Repository
public interface KeyboardRepository extends JpaRepository<KeyboardEntity, Long> {
}
