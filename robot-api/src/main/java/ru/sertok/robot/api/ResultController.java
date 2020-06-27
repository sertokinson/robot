package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/result")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ResultController {

    @GET
    @Path("/get/{testCase}")
    @ApiResponse(description = "Выгрузить все изображения по данному тесту")
    ResultResponse get(@PathParam("testCase") String testCase);

    @GET
    @Path("/get/{testCase}")
    @ApiResponse(description = "Выгрузить все изображения в папку")
    AppResponse toPath(@PathParam("testCase") String testCase);

    @GET
    @Path("/errors/{testCase}")
    @ApiResponse(description = "Выгрузить все ошибочные изображения по данному тесту")
    AppResponse errors(@PathParam("testCase") String testCase);
}
