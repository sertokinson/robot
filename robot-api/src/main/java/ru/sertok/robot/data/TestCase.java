package ru.sertok.robot.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sertok.robot.data.enumerate.TestStatus;

import java.util.Date;

@Setter
@Getter
@Builder
public class TestCase {
    /**
     * Название теста
     */
    private String testCaseName;

    /**
     * Описание теста
     */
    private String description;

    /**
     * Путь до приложения или браузера
     */
    private String pathToApp;

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
     * Является ли приложение браузером
     */
    private Boolean isBrowser;
}
