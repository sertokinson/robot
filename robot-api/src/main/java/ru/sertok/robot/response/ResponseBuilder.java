package ru.sertok.robot.response;

import ru.sertok.robot.data.enumerate.Status;

public class ResponseBuilder {
    public static BaseResponse success() {
        return BaseResponse.builder()
                .status(Status.SUCCESS)
                .build();
    }

    public static <T extends BaseResponse> T success(T response) {
        response.setStatus(Status.SUCCESS);
        return response;
    }

    public static <T extends BaseResponse> T warning(T response) {
        response.setStatus(Status.WARNING);
        return response;
    }

    public static <T extends BaseResponse> T error(String error) {
        return (T) BaseResponse.builder()
                .status(Status.ERROR)
                .error(error)
                .build();
    }


}
