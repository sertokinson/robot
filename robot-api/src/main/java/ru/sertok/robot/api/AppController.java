package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.sertok.robot.response.BaseResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public interface AppController {
    @GET
    @Path("/ping")
    @ApiResponses(@ApiResponse(
            responseCode = "200",
            content = @Content(examples = @ExampleObject("PONG")),
            description = "Приложение запущено"
    ))
    BaseResponse ping();

    @GET
    @Path("/pathToLog")
    @ApiResponses(@ApiResponse(
            responseCode = "200",
            description = "Путь до логов"
    ))
    BaseResponse pathToLog();

    @GET
    @Path("/version")
    @ApiResponses(@ApiResponse(
            responseCode = "200",
            description = "Версия приложения"
    ))
    BaseResponse version();
}
