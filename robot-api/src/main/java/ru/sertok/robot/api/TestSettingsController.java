package ru.sertok.robot.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TestSettingsController {
    @GET
    @Path("/browsers")
    Response browsers();

    @GET
    @Path("/desktops")
    Response desktops();

    @GET
    @Path("/urls")
    Response urls();
}
