package ru.sertok.robot.response;

import javax.ws.rs.core.Response;

public class ResponseBuilder {
    public static Response ok() {
        return Response.ok().build();
    }

    public static Response ok(Object entity) {
        return Response.ok(entity).build();
    }

    public static Response error(String error) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}
