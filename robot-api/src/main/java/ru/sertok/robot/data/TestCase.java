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
