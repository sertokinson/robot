package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.ScreenShotController;
import ru.sertok.robot.core.ScreenShot;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.storage.LocalStorage;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShotControllerImpl implements ScreenShotController {
    private final LocalStorage localStorage;
    private final ScreenShot screenShot;

    @Override
    public Response start(RobotRequest robotRequest) {
        log.debug("REST-запрос ../screenshot/start со значением {}", robotRequest);
        localStorage.setImage(screenShot.getImage());
        localStorage.setScreenshot(true);
        localStorage.setImages(new ArrayList<>());
        return Response.ok().build();
    }

    @Override
    public Response stop() {
        log.debug("REST-запрос ../screenshot/stop");
        localStorage.setScreenshot(false);
        return Response.ok().build();
    }
}
