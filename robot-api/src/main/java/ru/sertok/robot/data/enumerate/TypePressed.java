package ru.sertok.robot.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TypePressed {
    /**
     * Левая кнопка мыши
     */
    LEFT(1),
    /**
     * Правая кнопка мыши
     */
    RIGHT(2),
    UNKNOWN;

    private int code;

    public static TypePressed getType(int code) {
        for (TypePressed type :values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
