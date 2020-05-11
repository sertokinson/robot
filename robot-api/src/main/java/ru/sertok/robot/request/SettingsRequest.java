package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettingsRequest {
    @Parameter(required = true)
    private String pathToApp;
}
