package ru.sertok.robot.api;

import ru.sertok.robot.request.SettingsRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SettingsController {

    @POST
    @Path("/pathToApp")
    Response pathToApp(SettingsRequest settingsRequest);

}
