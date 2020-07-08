package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Image extends BaseData{
    private byte[] image;
    private boolean assertResult;
}
