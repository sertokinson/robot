package ru.sertok.robot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SettingsResponse {
    private List<String> values;
}
