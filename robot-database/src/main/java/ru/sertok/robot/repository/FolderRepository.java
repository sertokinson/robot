package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.FolderEntity;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    Optional<FolderEntity> findByName(String name);
    Optional<FolderEntity> findById(Long id);
}
