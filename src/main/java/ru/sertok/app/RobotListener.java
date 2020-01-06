package ru.sertok.app;

import org.jnativehook.GlobalScreen;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sertok.gui.RecordWindow;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotListener {

    private static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        logger.setLevel(Level.OFF);
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean("recordWindow",RecordWindow.class).getComponent().setVisible(true);


    }
}