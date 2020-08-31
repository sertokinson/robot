package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.request.SettingsRequest;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.BaseResponse;

import javax.ws.rs.*;
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

    @GET
    @Path("/settings")
    @ApiResponse(description = "Настройки приложения")
    AppResponse settings();

    @POST
    @Path("/saveSetting")
    @ApiResponse(description = "Настройки приложения")
    BaseResponse saveSetting(SettingsRequest settingsRequest);
}
