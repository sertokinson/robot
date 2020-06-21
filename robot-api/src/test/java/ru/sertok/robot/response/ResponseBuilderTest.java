package ru.sertok.robot.response;

import org.junit.Test;
import ru.sertok.robot.data.enumerate.Status;

import static org.junit.Assert.*;

public class ResponseBuilderTest {

    @Test
    public void success() {
        assertEquals(BaseResponse.builder().status(Status.SUCCESS).build(), ResponseBuilder.success());
    }

    @Test
    public void testSuccess() {
        assertEquals(AppResponse.builder().status(Status.SUCCESS).build(), ResponseBuilder.success(AppResponse.builder().build()));
    }

    @Test
    public void warning() {
        assertEquals(AppResponse.builder().status(Status.WARNING).build(), ResponseBuilder.warning(AppResponse.builder().build()));
    }

    @Test
    public void error() {
        assertEquals(AppResponse.builder().status(Status.ERROR).build(), ResponseBuilder.error(AppResponse.builder().build()));
    }

    @Test
    public void testError() {
        assertEquals(BaseResponse.builder().error("error").status(Status.ERROR).build(), ResponseBuilder.error("error"));
    }
}