package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.ResultResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/result")
@Produces(MediaType.APPLICATION_JSON)
public interface ResultController {

    @GET
    @Path("/get")
    @ApiResponse(description = "Выгрузить все изображения по данному тесту")
    ResultResponse get(@QueryParam("testCase") String testCase);

    @GET
    @Path("/toPath")
    @ApiResponse(description = "Выгрузить все изображения в папку")
    AppResponse toPath(@QueryParam("testCase") String testCase);
}
