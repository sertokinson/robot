package ru.sertok.robot.response;

import ru.sertok.robot.data.enumerate.Status;

public class ResponseBuilder {
    public static BaseResponse success() {
        return new BaseResponse(Status.SUCCESS);
    }

    public static <T extends BaseResponse> BaseResponse success(T response) {
        response.setStatus(Status.SUCCESS);
        return response;
    }

    public static <T extends BaseResponse> BaseResponse warning(T response) {
        response.setStatus(Status.WARNING);
        return response;
    }

    public static <T extends BaseResponse> BaseResponse error(T response) {
        response.setStatus(Status.ERROR);
        return response;
    }


}
