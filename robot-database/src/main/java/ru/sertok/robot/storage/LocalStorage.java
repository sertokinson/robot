package ru.sertok.robot.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
public class LocalStorage {
    private long startTime;
    private boolean activeCrop = false;
    private List<Object> steps = new ArrayList<>();
    private List<ImageEntity> images;
    private boolean screenshot = false;
    private Image image;
}
