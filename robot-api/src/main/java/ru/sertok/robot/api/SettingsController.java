package ru.sertok.robot.api;

import ru.sertok.robot.response.BaseResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SettingsController {
    @GET
    @Path("/browsers")
    BaseResponse browsers();

    @GET
    @Path("/desktops")
    BaseResponse desktops();

    @GET
    @Path("/urls")
    BaseResponse urls();
}
