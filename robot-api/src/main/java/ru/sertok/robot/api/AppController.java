package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.response.AppResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AppController {
    @GET
    @Path("/ping")
    @ApiResponse(description = "Проверка на то, что приложение запущенно")
    AppResponse ping();

    @GET
    @Path("/pathToLog")
    @ApiResponse(description = "Путь до логов")
    AppResponse pathToLog();

    @GET
    @Path("/version")
    @ApiResponse(description = "Версия приложения")
    AppResponse version();
}
