package ru.sertok.robot.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sertok.robot.entity.ImageEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
public class LocalStorage {
    private long startTime;
    private boolean activeCrop = false;
    private List<Object> steps = new ArrayList<>();
    private Point location;
    private Dimension size;
    private List<ImageEntity> images;
    private int timeScreenShot;
}
