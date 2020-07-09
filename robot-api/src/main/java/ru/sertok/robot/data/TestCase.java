package ru.sertok.robot.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.sertok.robot.data.enumerate.Platform;
import ru.sertok.robot.data.enumerate.TestStatus;

import java.util.Date;

@Setter
@Getter
@Builder
@EqualsAndHashCode
public class TestCase {
    /**
     * Название теста
     */
    private String testCaseName;

    /**
     * Имя папки
     */
    private String folderName;

    /**
     * Описание теста
     */
    private String description;

    /**
     * Путь до приложения или браузера
     */
    private String path;

    /**
     * Название тестируемого приложения или браузера
     */
    private String appName;

    /**
     * Тестируемый url - адрес сайта
     */
    private String url;

    /**
     * Длительность теста
     */
    private Integer time;

    /**
     * Статус выполнения теста
     */
    private TestStatus status;

    /**
     * Дата последнего запуска теста
     */
    private Date runDate;

    /**
     * Платформа тестируемого приложения
     */
    private Platform platform;
}
