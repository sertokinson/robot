package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sertok.robot.entity.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
}
