package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.sertok.robot.request.RobotRequest;
import ru.sertok.robot.response.RobotResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/robot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RobotController {

    @POST
    @Path("/start")
    Response start(RobotRequest robotRequest);

    @GET
    @Path("/get")
    @ApiResponses(@ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "*/*",
                    schema = @Schema(implementation = RobotResponse.class)
            ),
            description = "Список наименований тест кейсов"
    ))
    Response get();
}
