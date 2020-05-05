package ru.sertok.robot.api;

import ru.sertok.robot.request.RecordRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/record")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RecordController {

    @POST
    @Path("/start")
    Response start(RecordRequest recordRequest);

    @GET
    @Path("/stop")
    Response stop();
}
