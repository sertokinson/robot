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
     * Тестируемый url - адрес сайта
     */
    private String url;


    /**
     * Хост сервера
     */
    private String host;
}
