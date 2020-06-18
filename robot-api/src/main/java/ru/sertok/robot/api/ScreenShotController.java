package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.request.ScreenShotRequest;
import ru.sertok.robot.response.BaseResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/screenshot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ScreenShotController {

    @POST
    @Path("/start")
    @ApiResponse(description = "Старт записи скриншота")
    BaseResponse start(ScreenShotRequest screenShotRequest);

    @POST
    @Path("/stop")
    @ApiResponse(description = "Стоп записи скриншота")
    BaseResponse stop();

    @POST
    @Path("/crop")
    @ApiResponse(description = "Нажатие на кнопку кроп")
    BaseResponse crop();
}
