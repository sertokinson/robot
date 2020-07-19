package ru.sertok.robot.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.sertok.robot.data.enumerate.Platform;
import ru.sertok.robot.data.enumerate.TestStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEST_CASE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
)
public class TestCaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * наименование тест кейса
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Описание теста
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     *  Время выполнения теста
     */
    @Column(name = "TIME")
    private Long time;

    /**
     *  Дата последнего запуска
     */
    @Column(name = "RUN_DATE")
    private Date runDate;

    /**
     * Статус выполнения теста
     */
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TestStatus status;

    /**
     * Платформа тестируемого приложения
     */
    @Column(name = "PLATFORM", nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    /**
     * Ссылка на браузер, если есть
     */
    @Column(name = "BROWSER_ID")
    private Long browserId;

    /**
     * Ссылка на url, если есть
     */
    @Column(name = "URL_ID")
    private Long urlId;

    /**
     * Ссылка на desktop, если есть
     */
    @Column(name = "DESKTOP_ID")
    private Long desktopId;

    /**
     * Ссылка на папку
     */
    @Column(name = "FOLDER_ID", nullable = false)
    private Long folderId;

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
