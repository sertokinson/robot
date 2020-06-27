package ru.sertok.robot.app;

import org.jnativehook.GlobalScreen;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("ru.sertok.robot")
@EnableJpaRepositories(basePackages = "ru.sertok.robot.repository")
public class RobotApp {
    private static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        logger.setLevel(Level.OFF);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(RobotApp.class);
        builder.headless(false);
        builder.run(args);
    }
}