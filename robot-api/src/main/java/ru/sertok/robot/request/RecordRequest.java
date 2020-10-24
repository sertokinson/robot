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
    private String name;

    /**
     * Имя папки
     */
    private String folderName;

    /**
     * Наименование браузера
     */
    private String browserName;

    /**
     * Тестируемый url - адрес сайта
     */
    private String url;

    /**
     * Описание теста
     */
    private String description;

    /**
     * Хост сервера
     */
    private String host;
}
