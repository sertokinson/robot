package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
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
    @Parameter(required = true)
    private String testCaseName;

    /**
     * Путь до приложения или браузера
     */
    @Parameter(required = true)
    private String pathToApp;

    /**
     * Название тестируемого приложения или браузера
     */
    @Parameter(required = true)
    private String appName;

    /**
     * Является ли приложение браузером
     */
    @Parameter(required = true)
    private Boolean isBrowser;

    /**
     * Тестируемый url - адрес сайта
     */
    private String url;

    /**
     * Описание теста
     */
    private String description;



}
