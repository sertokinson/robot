package ru.sertok.robot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.sertok.robot.data.enumerate.BrowserName;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "TEST_CASE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class TestCaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * наименование тест кейса
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * url сайта который тестируется
     */
    @Column(name = "URL")
    private String url;

    /**
     * путь до приложения которое тестируется
     */
    @Column(name = "PATH")
    private String path;

    /**
     * Браузер в котором тестируется приложение
     */
    @Column(name = "BROWSER_NAME")
    private String  browserName;

    /**
     * версия браузера
     */
    @Column(name = "BROWSER_VERSION")
    private String browserVersion;

    /**
     * ОС в катором тестируется приложение
     */
    @Column(name = "OS")
    private String os;

    /**
     *  Время выполнения теста
     */
    @Column(name = "TIME")
    private Integer time;

    /**
     * Все события мыши
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "testCase", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MouseEntity> mouse;

    /**
     * Все события клавиатуры
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "testCase", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<KeyboardEntity> keyboard;

    /**
     * Все изображения
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "testCase", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ImageEntity> images;

    /**
     * Все события screenshot
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "testCase", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ScreenShotEntity> screenShots;
}
