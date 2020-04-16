package ru.sertok.robot.assertation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sertok.robot.assertation.gui.AssertComponent;
import ru.sertok.robot.config.Config;

public class StartAssert {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean("assertComponent", AssertComponent.class).getComponent().setVisible(true);
    }
}
