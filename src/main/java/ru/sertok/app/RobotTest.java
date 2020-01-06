package ru.sertok.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sertok.gui.RobotWindow;

public class RobotTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean("robotWindow", RobotWindow.class).getComponent().setVisible(true);
    }
}
