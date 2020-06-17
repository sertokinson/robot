package ru.sertok.robot.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class SettingsResponse extends BaseResponse{
    private List<String> values;
}
