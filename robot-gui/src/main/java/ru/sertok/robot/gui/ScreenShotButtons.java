package ru.sertok.robot.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ScreenShotButtons extends JFrame {
    @Autowired
    private TranslucentWindow tw;

    public ScreenShotButtons() {
        setLayout(new GridBagLayout());
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 1));
        JButton start = new JButton("start");
        start.addActionListener(action -> {
            //TODO запрос .../screenshot/start
        });
        JButton stop = new JButton("stop");
        stop.addActionListener(action -> {
            //TODO запрос .../screenshot/stop
            this.setVisible(false);
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
