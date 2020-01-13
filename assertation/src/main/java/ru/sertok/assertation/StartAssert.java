package ru.sertok.assertation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sertok.assertation.gui.AssertComponent;

public class StartAssert {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean("assertComponent", AssertComponent.class).getComponent().setVisible(true);
    }
}
