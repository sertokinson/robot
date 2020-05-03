package ru.sertok.robot.listener.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.storage.LocalStorage;

import javax.swing.*;
import java.awt.*;

@Component
public class RecordButtons extends JFrame {
    @Autowired
    private LocalStorage localStorage;
    @Autowired
    private TranslucentWindow tw;


    private JButton start;

    public RecordButtons(ScreenShot screenShot) {
        setLayout(new GridBagLayout());
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 1));
        start = new JButton("start");
        JButton stop = new JButton("stop");
        stop.addActionListener(action -> {
            screenShot.stop();
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

    JButton getStartButton() {
        return start;
    }
}
