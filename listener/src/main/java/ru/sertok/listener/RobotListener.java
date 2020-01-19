package ru.sertok.listener;

import org.jnativehook.GlobalScreen;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sertok.listener.gui.RecordWindow;
import ru.sertok.robot.config.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotListener {

    private static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        logger.setLevel(Level.OFF);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean("recordWindow", RecordWindow.class).getComponent().setVisible(true);
    }
}