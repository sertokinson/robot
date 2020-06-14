package ru.sertok.robot.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/image")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("text/html; charset=UTF-8")
public interface ImageOutputController {
    @GET
    @Path("/getAll")
    Response getAll(String testCase);

    @GET
    @Path("/getErrors")
    Response getErrors(String testCase);
}
