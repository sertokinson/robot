package ru.sertok.robot.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.api.RobotController;
import ru.sertok.robot.api.ScreenShotController;
import ru.sertok.robot.api.SettingsController;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RecordController.class);
        register(RobotController.class);
        register(ScreenShotController.class);
        register(SettingsController.class);
    }
}
