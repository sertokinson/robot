package ru.sertok.robot.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SettingsResponse extends BaseResponse {
    private List<String> values;
}
