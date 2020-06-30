package ru.sertok.robot.data;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private byte[] image;
    private boolean assertResult;
}
