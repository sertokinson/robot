package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import ru.sertok.robot.data.App;
import ru.sertok.robot.data.Url;

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
     * Является ли приложение браузером
     */
    @Parameter(required = true)
    private Boolean isBrowser;

    /**
     * Тестируемое приложение или браузер
     */
    private App app;

    /**
     * Тестируемый url - адрес сайта
     */
    private Url url;

    /**
     * Описание теста
     */
    private String description;



}
