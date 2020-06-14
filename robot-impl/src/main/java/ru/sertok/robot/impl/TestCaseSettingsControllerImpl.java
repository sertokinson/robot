package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.TestSettingsController;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.response.SettingsResponse;
import ru.sertok.robot.service.SettingsService;

import javax.ws.rs.core.Response;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestCaseSettingsControllerImpl implements TestSettingsController {
    private final SettingsService settingsService;

    @Override
    public Response browsers() {
        log.debug("REST-запрос ../settings/browsers");
        return ResponseBuilder.ok(new SettingsResponse(settingsService.getBrowsers()));
    }

    @Override
    public Response desktops() {
        log.debug("REST-запрос ../settings/desktops");
        return ResponseBuilder.ok(new SettingsResponse(settingsService.getDesktops()));
    }

    @Override
    public Response urls() {
        log.debug("REST-запрос ../settings/urls");
        return ResponseBuilder.ok(new SettingsResponse(settingsService.getUrls()));
    }
}
