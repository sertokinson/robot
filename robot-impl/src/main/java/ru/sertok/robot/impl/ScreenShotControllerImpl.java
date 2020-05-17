package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.ScreenShotController;
import ru.sertok.robot.core.ScreenShot;
import ru.sertok.robot.mapper.ScreenShotMapper;
import ru.sertok.robot.request.ScreenShotRequest;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.storage.LocalStorage;

import javax.ws.rs.core.Response;

import static ru.sertok.robot.utils.Utils.deleteLastMousePressed;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShotControllerImpl implements ScreenShotController {
    private final LocalStorage localStorage;
    private final ScreenShot screenShot;
    private final ScreenShotMapper screenShotMapper;

    @Override
    public Response start(ScreenShotRequest screenShotRequest) {
        log.debug("REST-запрос ../screenshot/start со значением {}", screenShotRequest);
        deleteLastMousePressed(localStorage.getSteps());
        screenShot.setSize(screenShotMapper.toImage(screenShotRequest));
        localStorage.setScreenshotStart(true);
        return ResponseBuilder.ok();
    }

    @Override
    public Response stop() {
        log.debug("REST-запрос ../screenshot/stop");
        deleteLastMousePressed(localStorage.getSteps());
        localStorage.setScreenshotStart(false);
        return ResponseBuilder.ok();
    }

    @Override
    public Response crop() {
        log.debug("REST-запрос ../screenshot/crop (произошло нажатие кнопки crop)");
        deleteLastMousePressed(localStorage.getSteps());
        return ResponseBuilder.ok();
    }
}