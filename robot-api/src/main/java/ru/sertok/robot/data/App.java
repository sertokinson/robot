package ru.sertok.robot.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class App extends Template {
    /**
     * Название тестируемого приложения или браузера
     */
    private String name;
    /**
     * Путь до приложения или браузера
     */
    private String path;
}
