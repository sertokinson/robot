package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.sertok.robot.request.RobotRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/image")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("text/html; charset=UTF-8")
public interface ImageOutputController {
    @POST
    @Path("/getAll")
    Response getAll(@RequestBody(required = true) RobotRequest robotRequest);

    @POST
    @Path("/getErrors")
    Response getErrors(@RequestBody(required = true) RobotRequest robotRequest);
}
