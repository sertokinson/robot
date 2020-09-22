package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.SettingsEntity;

public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
}
