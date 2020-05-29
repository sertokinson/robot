package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecordRequest {
    @Parameter(required = true)
    private String testCaseName;
    @Parameter(required = true)
    private String url;
    @Parameter(required = true)
    private String pathToApp;
    private String description;
}
