package ru.sertok.robot.request;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordRequest {
    /**
     * Название теста
     */
    private String testCaseName;

    /**
     * Название приложения или браузера
     */
    private String appName;

    /**
     * Имя папки
     */
    private String folderName;

    /**
     * Является ли приложение браузером
     */
    private Boolean isBrowser;

    /**
     * Путь до приложения или браузера
     */
    private String path;

    /**
     * Тестируемый url - адрес сайта
     */
    private String url;

    /**
     * Описание теста
     */
    private String description;

}
