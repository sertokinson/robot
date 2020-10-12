package ru.sertok.robot.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.AppController;
import ru.sertok.robot.entity.SettingsEntity;
import ru.sertok.robot.repository.SettingsRepository;
import ru.sertok.robot.request.SettingsRequest;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.BaseResponse;
import ru.sertok.robot.response.ResponseBuilder;

import java.util.List;

@Controller
public class AppControllerImpl implements AppController {
    @Autowired
    private SettingsRepository settingsRepository;

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
                .result("0.25-alpha")
                .build());
    }

    @Override
    public AppResponse settings() {
        List<SettingsEntity> settings = settingsRepository.findAll();
        String host = "http://192.168.1.67:8080";
        if (!settings.isEmpty())
            host = settings.get(0).getHost();
        return ResponseBuilder.success(AppResponse.builder()
                .result(host)
                .build());
    }

    @Override
    public BaseResponse saveSetting(SettingsRequest settingsRequest) {
        settingsRepository.deleteAll();
        settingsRepository.save(SettingsEntity.builder()
                .host(settingsRequest.getHost())
                .build());
        return ResponseBuilder.success();
    }
}
