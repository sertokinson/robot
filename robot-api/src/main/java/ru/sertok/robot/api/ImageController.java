package ru.sertok.robot.api;

import ru.sertok.robot.response.BaseResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/image")
@Consumes(MediaType.APPLICATION_JSON)
@Produces("text/html; charset=UTF-8")
public interface ImageController {
    @GET
    @Path("/getAll")
    BaseResponse getAll(String testCase);

    @GET
    @Path("/getErrors")
    BaseResponse getErrors(String testCase);
}
