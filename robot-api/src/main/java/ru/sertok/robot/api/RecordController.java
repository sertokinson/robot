package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.response.BaseResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/record")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RecordController {

    @POST
    @Path("/start")
    @ApiResponse(description = "Старт записи теста")
    BaseResponse start(RecordRequest recordRequest);

    @GET
    @Path("/stop")
    @ApiResponse(description = "Стоп записи теста")
    BaseResponse stop() throws InterruptedException;

    @POST
    @Path("/exit")
    @ApiResponse(description = "Выход из записи теста")
    BaseResponse exit();
}
