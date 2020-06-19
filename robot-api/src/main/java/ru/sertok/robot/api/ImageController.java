package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.response.AppResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/image")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ImageController {

    @GET
    @Path("/getAll/{testCase}")
    @ApiResponse(description = "Выгрузить все изображения по данному тесту")
    AppResponse getAll(@PathParam("testCase") String testCase);

    @GET
    @Path("/getErrors/{testCase}")
    @ApiResponse(description = "Выгрузить все ошибочные изображения по данному тесту")
    AppResponse getErrors(@PathParam("testCase")String testCase);
}
