package ru.sertok.robot.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;
import ru.sertok.robot.api.*;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RecordController.class);
        register(RobotController.class);
        register(ScreenShotController.class);
        register(AppController.class);
        register(ImageOutputController.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}
