package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.BrowserEntity;

import java.util.Optional;

public interface BrowserRepository extends JpaRepository<BrowserEntity, Long> {
    Optional<BrowserEntity> findByName(String name);
}
