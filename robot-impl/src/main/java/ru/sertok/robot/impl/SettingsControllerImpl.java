package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.SettingsController;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.response.SettingsResponse;
import ru.sertok.robot.service.SettingsService;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsControllerImpl implements SettingsController {
    private final SettingsService settingsService;

    @Override
    public SettingsResponse browsers() {
        log.debug("REST-запрос ../settings/browsers");
        return ResponseBuilder.success(SettingsResponse.builder()
                .values(settingsService.getBrowsers())
                .build());
    }

    @Override
    public SettingsResponse desktops() {
        log.debug("REST-запрос ../settings/desktops");
        return ResponseBuilder.success(SettingsResponse.builder()
                .values(settingsService.getDesktops())
                .build());
    }

    @Override
    public SettingsResponse urls() {
        log.debug("REST-запрос ../settings/urls");
        return ResponseBuilder.success(SettingsResponse.builder()
                .values(settingsService.getUrls())
                .build());
    }
}
