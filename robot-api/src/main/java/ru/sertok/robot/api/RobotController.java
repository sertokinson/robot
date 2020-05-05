package ru.sertok.robot.api;

import ru.sertok.robot.request.RobotRequest;

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
    Response get();
}
