package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.UrlEntity;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByUrl(String url);
}
