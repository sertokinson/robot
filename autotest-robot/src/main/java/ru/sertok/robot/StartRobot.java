package ru.sertok.robot;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sertok.robot.config.Config;
import ru.sertok.robot.gui.RobotWindow;

public class StartRobot {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean("robotWindow", RobotWindow.class).getComponent().setVisible(true);
    }
}
