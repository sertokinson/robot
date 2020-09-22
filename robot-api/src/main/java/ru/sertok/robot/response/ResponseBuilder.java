package ru.sertok.robot.response;

import ru.sertok.robot.data.Status;

public class ResponseBuilder {

    private ResponseBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static BaseResponse success() {
        return BaseResponse.builder()
                .status(Status.SUCCESS)
                .build();
    }

    public static <T extends BaseResponse> T success(T response) {
        response.setStatus(Status.SUCCESS);
        return response;
    }

    public static <T extends BaseResponse> T error(T response) {
        response.setStatus(Status.ERROR);
        return response;
    }

    public static BaseResponse error(String error) {
        return BaseResponse.builder()
                .status(Status.ERROR)
                .error(error)
                .build();
    }
}
