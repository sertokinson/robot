package ru.sertok.robot.api;

import ru.sertok.robot.request.ScreenShotRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/screenshot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ScreenShotController {

    @POST
    @Path("/start")
    Response start(ScreenShotRequest screenShotRequest);

    @POST
    @Path("/stop")
    Response stop();
}
