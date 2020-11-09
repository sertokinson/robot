package ru.sertok.robot.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static ResponseEntity success(){
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<ErrorResponse> error(String error){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(error));
    }
}
