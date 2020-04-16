package ru.sertok.robot.listener.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;

import javax.swing.*;
import java.awt.*;

@Component
public class RecordButtons extends JFrame {
    @Autowired
    private ScreenShot screenShot;
    private JButton start;

    public RecordButtons() {
        super("RecordButtons");
        setLayout(new GridBagLayout());
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 1));
        start = new JButton("start");
        JButton stop = new JButton("stop");
        stop.addActionListener(action -> screenShot.stop());
        container.add(start);
        container.add(stop);
        this.setSize(60, 50);
        this.setAlwaysOnTop(true);
        this.setFocusable(true);
        this.setUndecorated(true);

    }

    public JButton getStartButton() {
        return start;
    }
}
