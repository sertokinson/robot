package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.*;

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
    @Path("/getFolders")
    @ApiResponse(description = "Получить все тесты и папки")
    FoldersResponse getFolders();

    @GET
    @Path("/get/{testCase}")
    @ApiResponse(description = "Получить тест кейс")
    TestCaseResponse get(@PathParam("testCase") String testCase);

    @GET
    @Path("/getAll/{testCase}")
    @ApiResponse(description = "Получить тест кейс по имени")
    TestCaseResponse getAll(@PathParam("testCase") String testCase);

}
