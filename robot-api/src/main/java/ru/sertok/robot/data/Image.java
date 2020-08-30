package ru.sertok.robot.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseData{
    private byte[] image;
    private boolean assertResult;
}
