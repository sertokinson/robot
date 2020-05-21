package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public interface AppController {
    @GET
    @Path("/ping")
    @ApiResponses(@ApiResponse(
            responseCode = "200",
            content = @Content(examples = @ExampleObject("PONG")),
            description = "Приложение запущено"
    ))
    Response ping();

    @GET
    @Path("/pathToLog")
    @ApiResponses(@ApiResponse(
            responseCode = "200",
            description = "Путь до логов"
    ))
    Response pathToLog();

}
