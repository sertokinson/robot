package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.BaseResponse;
import ru.sertok.robot.response.RobotResponse;
import ru.sertok.robot.response.TestCaseResponse;
import ru.sertok.robot.response.TestCasesResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/robot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RobotController {

    @POST
    @Path("/start")
    @ApiResponse(description = "Воспроизведение теста")
    RobotResponse start(RobotRequest robotRequest);

    @POST
    @Path("/delete")
    @ApiResponse(description = "Удаление теста")
    BaseResponse delete(RobotRequest robotRequest);

    @GET
    @Path("/getAll")
    @ApiResponse(description = "Получить все тесты")
    TestCasesResponse getAll();

    @GET
    @Path("/getAll/{testCase}")
    @ApiResponse(description = "Получить тест кейс по имени")
    TestCaseResponse getAll(String testCase);

    @GET
    @Path("/get/{testCase}")
    @ApiResponse(description = "Получить тест кейс")
    TestCaseResponse get(String testCase);
}
