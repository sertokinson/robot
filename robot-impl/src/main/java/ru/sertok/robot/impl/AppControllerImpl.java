package ru.sertok.robot.impl;

import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.AppController;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.BaseResponse;
import ru.sertok.robot.response.ResponseBuilder;

@Controller
public class AppControllerImpl implements AppController {
    @Override
    public BaseResponse ping() {
        return ResponseBuilder.success(AppResponse.builder()
                .result("PONG")
                .build());
    }

    @Override
    public BaseResponse pathToLog() {
        return ResponseBuilder.success(AppResponse.builder()
                .result(System.getProperty("java.io.tmpdir") + "robot.log")
                .build());
    }

    @Override
    public BaseResponse version() {
        return ResponseBuilder.success(AppResponse.builder()
                .result("0.20")
                .build());
    }
}
