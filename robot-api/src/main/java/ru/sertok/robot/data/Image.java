package ru.sertok.robot.data;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private int x;
    private int y;
    private int width;
    private int height;
    private byte[] expectedImage;
    private byte[] actualImage;
}