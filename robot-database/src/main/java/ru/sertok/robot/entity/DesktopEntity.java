package ru.sertok.robot.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DESKTOP",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class DesktopEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * имя приложения
     */
    @Column(name = "NAME")
    private String name;

    /**
     * путь до приложения
     */
    @Column(name = "PATH")
    private String path;

    /**
     * Все тесты которые тестируют это приложение
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "desktop", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TestCaseEntity> testCases;
}
