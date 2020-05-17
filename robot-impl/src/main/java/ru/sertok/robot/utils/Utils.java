package ru.sertok.robot.utils;

import lombok.extern.slf4j.Slf4j;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;

import java.util.List;

@Slf4j
public class Utils {
    public static void deleteLastMousePressed(List<BaseData> steps) {
        log.debug("Удаляем последнее нажатие");
        for (int i = steps.size() - 1; i >= 0; i--) {
            if (steps.get(i) instanceof Mouse) {
                if (((Mouse) steps.get(i)).getType() == Type.PRESSED) {
                    steps.remove(i);
                    break;
                }
            }
            steps.remove(i);
        }
    }
}
