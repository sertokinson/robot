package ru.sertok.robot.data;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestCase {
    /**
     * Название
     */
    String name;

    /**
     * Имя папки
     */
    String folderName;

    /**
     * Наименование браузера
     */
    String browserName;

    /**
     * Описание
     */
    String description;

    /**
     * Тестируемый url - адрес сайта
     */
    String url;

    /**
     * Хост сервера
     */
    String host;
}
