package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/result")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ResultController {

    @GET
    @Path("/get")
    @ApiResponse(description = "Выгрузить все изображения по данному тесту")
    ResultResponse get(RobotRequest robotRequest);

    @GET
    @Path("/toPath")
    @ApiResponse(description = "Выгрузить все изображения в папку")
    AppResponse toPath(RobotRequest robotRequest);

    @GET
    @Path("/errors")
    @ApiResponse(description = "Выгрузить все ошибочные изображения по данному тесту")
    AppResponse errors(RobotRequest robotRequest);
}
