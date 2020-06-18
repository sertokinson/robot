package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.response.AppResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/image")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ImageController {

    @GET
    @Path("/getAll")
    @ApiResponse(description = "Выгрузить все изображения по данному тесту")
    AppResponse getAll(String testCase);

    @GET
    @Path("/getErrors")
    @ApiResponse(description = "Выгрузить все ошибочные изображения по данному тесту")
    AppResponse getErrors(String testCase);
}
