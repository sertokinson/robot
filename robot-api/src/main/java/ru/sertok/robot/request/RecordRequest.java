package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecordRequest {
    @Parameter(required = true)
    private String testCaseName;
    @Parameter(required = true)
    private String url;
}
