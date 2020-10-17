package ru.sertok.robot.app;

import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@RestController
@SpringBootApplication
@ComponentScan("ru.sertok.robot")
@EnableJpaRepositories(basePackages = "ru.sertok.robot.repository")
public class RobotApp {
    private static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public static void main(String[] args) {
        logger.setLevel(Level.OFF);
        String path = "chromedriver.exe";
        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            path = "chromedriver";

        System.setProperty("webdriver.chrome.driver", path);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(RobotApp.class);
        builder.headless(false);
        builder.run(args);
    }
}