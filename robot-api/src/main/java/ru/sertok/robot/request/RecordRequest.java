package ru.sertok.robot.request;

import lombok.*;
import ru.sertok.robot.data.App;

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
     * Является ли приложение браузером
     */
    private Boolean isBrowser;

    /**
     * Тестируемое приложение или браузер
     */
    private App app;

    /**
     * Тестируемый url - адрес сайта
     */
    private String url;

    /**
     * Описание теста
     */
    private String description;

}
