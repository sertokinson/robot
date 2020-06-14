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
@Table(name = "BROWSER",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class BrowserEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Браузер в котором тестируется приложение
     */
    @Column(name = "NAME")
    private String name;

    /**
     * путь до браузера
     */
    @Column(name = "PATH")
    private String path;

    /**
     * Все тесты которые тестируют на этом браузере
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "browser", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TestCaseEntity> testCases;
}
