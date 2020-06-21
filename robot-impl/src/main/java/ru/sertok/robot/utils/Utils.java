package ru.sertok.robot.utils;

import lombok.extern.slf4j.Slf4j;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;

import java.util.List;

@Slf4j
public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static void deleteLastMousePressed(List<BaseData> steps) {
        log.debug("Удаляем последнее нажатие");
        if (!steps.isEmpty())
            for (int i = steps.size() - 1; i >= 0; i--) {
                if (steps.get(i) instanceof Mouse && steps.get(i).getType() == Type.PRESSED) {
                    steps.remove(i);
                    for (int j = steps.size() - 1; j >= 0; j--) {
                        if (steps.get(j) instanceof Mouse && steps.get(j).getType() == Type.RELEASED) {
                            steps.remove(j);
                            break;
                        }
                    }
                    break;
                }
            }
    }
}
