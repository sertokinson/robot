package ru.sertok.robot.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    Response pathToApp(@RequestBody(required = true) SettingsRequest settingsRequest);

}
