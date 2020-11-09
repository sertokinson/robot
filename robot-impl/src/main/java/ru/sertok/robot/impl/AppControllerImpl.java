package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.sertok.robot.api.AppController;
import ru.sertok.robot.entity.SettingsEntity;
import ru.sertok.robot.repository.SettingsRepository;
import ru.sertok.robot.request.SettingsRequest;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.ResponseBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppControllerImpl implements AppController {
    private final SettingsRepository settingsRepository;

    @Override
    public ResponseEntity<AppResponse> ping() {
        return ResponseEntity.ok(new AppResponse("PONG"));
    }

    @Override
    public ResponseEntity<AppResponse> pathToLog() {
        return ResponseEntity.ok(new AppResponse(System.getProperty("java.io.tmpdir") + "robot.log"));
    }

    @Override
    public ResponseEntity<AppResponse> version() {
        return ResponseEntity.ok(new AppResponse("0.26"));
    }

    @Override
    public ResponseEntity<AppResponse> settings() {
        List<SettingsEntity> settings = settingsRepository.findAll();
        String host = "http://localhost:8090";
        if (!settings.isEmpty())
            host = settings.get(0).getHost();
        return ResponseEntity.ok(new AppResponse(host));
    }

    @Override
    public ResponseEntity saveSetting(SettingsRequest settingsRequest) {
        settingsRepository.deleteAll();
        settingsRepository.save(SettingsEntity.builder()
                .host(settingsRequest.getHost())
                .build());
        return ResponseBuilder.success();
    }
}
