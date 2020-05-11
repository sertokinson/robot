package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.sertok.robot.request.RecordRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/record")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RecordController {

    @POST
    @Path("/start")
    Response start(@RequestBody(required = true) RecordRequest recordRequest);

    @POST
    @Path("/stop")
    Response stop();
}
