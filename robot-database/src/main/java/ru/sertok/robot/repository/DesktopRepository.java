package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.BrowserEntity;
import ru.sertok.robot.entity.DesktopEntity;

import java.util.Optional;

public interface DesktopRepository extends JpaRepository<DesktopEntity, Long> {
    Optional<DesktopEntity> findByName(String name);
}
