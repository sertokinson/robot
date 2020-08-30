package ru.sertok.robot.app;

import org.jnativehook.GlobalScreen;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan("ru.sertok.robot")
public class RobotApp {
    private static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        logger.setLevel(Level.OFF);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(RobotApp.class);
        builder.headless(false);
        builder.run(args);
    }
}