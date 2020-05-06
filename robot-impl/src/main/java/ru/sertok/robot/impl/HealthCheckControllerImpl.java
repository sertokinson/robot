package ru.sertok.robot.impl;

import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.HealthCheckController;
import ru.sertok.robot.response.ResponseBuilder;

import javax.ws.rs.core.Response;

@Controller
public class HealthCheckControllerImpl implements HealthCheckController {
    @Override
    public Response ping() {
        return ResponseBuilder.ok("PONG");
    }
}
