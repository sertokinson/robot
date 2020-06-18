package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.response.SettingsResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SettingsController {
    @GET
    @Path("/browsers")
    @ApiResponse(description = "Получить все доступные браузеры")
    SettingsResponse browsers();

    @GET
    @Path("/desktops")
    @ApiResponse(description = "Получить все доступные приложения")
    SettingsResponse desktops();

    @GET
    @Path("/urls")
    @ApiResponse(description = "Получить все доступные urls")
    SettingsResponse urls();
}
