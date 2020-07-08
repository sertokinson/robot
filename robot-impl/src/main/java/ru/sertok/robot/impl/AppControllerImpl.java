package ru.sertok.robot.impl;

import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.AppController;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.ResponseBuilder;

@Controller
public class AppControllerImpl implements AppController {
    @Override
    public AppResponse ping() {
        return ResponseBuilder.success(AppResponse.builder()
                .result("PONG")
                .build());
    }

    @Override
    public AppResponse pathToLog() {
        return ResponseBuilder.success(AppResponse.builder()
                .result(System.getProperty("java.io.tmpdir") + "robot.log")
                .build());
    }

    @Override
    public AppResponse version() {
        return ResponseBuilder.success(AppResponse.builder()
                .result("0.21-alpha")
                .build());
    }
}
