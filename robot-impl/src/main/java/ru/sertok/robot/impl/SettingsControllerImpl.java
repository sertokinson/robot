package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.SettingsController;
import ru.sertok.robot.core.ExecuteApp;
import ru.sertok.robot.request.SettingsRequest;

import javax.ws.rs.core.Response;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsControllerImpl implements SettingsController {
    private final ExecuteApp executeApp;

    @Override
    public Response pathToApp(SettingsRequest settingsRequest) {
        String pathToApp = settingsRequest.getPathToApp();
        log.debug("REST-запрос ../settings/pathToApp со значением {}", pathToApp);
        executeApp.setPathToApp(pathToApp);
        return Response.ok().build();
    }
}
