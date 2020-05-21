package ru.sertok.robot.impl;

import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.AppController;
import ru.sertok.robot.response.ResponseBuilder;

import javax.ws.rs.core.Response;

@Controller
public class AppControllerImpl implements AppController {
    @Override
    public Response ping() {
        return ResponseBuilder.ok("PONG");
    }

    @Override
    public Response pathToLog() {
        return ResponseBuilder.ok(System.getProperty("java.io.tmpdir") + "robot.log");
    }
}
