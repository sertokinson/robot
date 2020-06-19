package ru.sertok.robot.utils;

import lombok.extern.slf4j.Slf4j;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Utils {
    public static void deleteLastMousePressed(List<BaseData> steps) {
        log.debug("Удаляем последнее нажатие");
        List<BaseData> mouseSteps = steps.stream().filter(step -> step instanceof Mouse).collect(Collectors.toList());
        if (!mouseSteps.isEmpty() && mouseSteps.stream().anyMatch(step -> ((Mouse) step).getType() == Type.PRESSED))
            for (int i = mouseSteps.size() - 1; i >= 0; i--) {
                if (((Mouse) mouseSteps.get(i)).getType() == Type.PRESSED) {
                    mouseSteps.remove(i);
                    break;
                }
                mouseSteps.remove(i);
            }
    }
}
