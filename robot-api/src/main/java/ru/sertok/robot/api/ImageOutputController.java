package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ru.sertok.robot.request.ImageOutputRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/image")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ImageOutputController {
    @POST
    @Path("/output")
    Response output(@RequestBody(required = true) ImageOutputRequest imageOutputRequest);
}
