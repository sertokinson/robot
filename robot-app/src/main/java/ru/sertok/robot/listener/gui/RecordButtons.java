package ru.sertok.robot.listener.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.storage.LocalStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Component
public class RecordButtons extends JFrame {
    @Autowired
    private LocalStorage localStorage;
    @Autowired
    private TranslucentWindow tw;
    @Autowired
    private ScreenShot screenShot;

    public RecordButtons() {
        setLayout(new GridBagLayout());
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 1));
        JButton start = new JButton("start");
        start.addActionListener(action -> {
            localStorage.setImage(screenShot.getImage());
            localStorage.setScreenshot(true);
            localStorage.setImages(new ArrayList<>());
        });
        JButton stop = new JButton("stop");
        stop.addActionListener(action -> {
            localStorage.setScreenshot(false);
            this.setVisible(false);
            localStorage.setActiveCrop(false);
            tw.dispose();
        });
        container.add(start);
        container.add(stop);
        this.setSize(60, 50);
        this.setAlwaysOnTop(true);
        this.setFocusable(true);
        this.setUndecorated(true);
    }

}
