package ru.sertok.robot.data;

import lombok.*;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {
    /**
     * Название
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
     * Описание
     */
    private String description;

    /**
     * Тестируемый url - адрес сайта
     */
    private String url;

    /**
     * Хост сервера
     */
    private String host;
}
