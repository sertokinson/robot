package ru.sertok.robot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sertok.robot.gui.RobotWindow;

public class StartRobot {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean("robotWindow", RobotWindow.class).getComponent().setVisible(true);
    }
}
