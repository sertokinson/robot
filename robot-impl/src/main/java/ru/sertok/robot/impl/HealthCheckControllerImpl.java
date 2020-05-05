package ru.sertok.robot.impl;

import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.HealthCheckController;

import javax.ws.rs.core.Response;

@Controller
public class HealthCheckControllerImpl implements HealthCheckController {
    @Override
    public Response ping() {
        return Response.ok("PONG").build();
    }
}
