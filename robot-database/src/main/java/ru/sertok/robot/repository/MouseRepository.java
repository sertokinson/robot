package ru.sertok.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sertok.robot.entity.MouseEntity;

@Repository
public interface MouseRepository extends JpaRepository<MouseEntity, Long> {
}
