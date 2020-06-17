package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    BaseResponse start(@RequestBody(required = true) RecordRequest recordRequest);

    @POST
    @Path("/continued")
    BaseResponse continued();

    @GET
    @Path("/stop")
    BaseResponse stop(@HeaderParam("user-agent") String userAgent);

    @POST
    @Path("/exit")
    BaseResponse exit();
}
