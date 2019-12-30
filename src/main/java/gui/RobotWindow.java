package gui;

import com.google.gson.internal.LinkedTreeMap;
import hook.Database;
import hook.KeyEvents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RobotWindow extends JFrame {
    private static JComboBox selectBox;

    public RobotWindow() throws HeadlessException {
        super("Robot");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        JLabel label = new JLabel("Выберите тест кейс: ");
        container.add(label);
        File dir = new File("database");
        List<File> files = Arrays.asList(dir.listFiles());
        selectBox = new JComboBox(files.stream().map(f -> f.getName().split(".json")[0]).toArray());
        container.add(selectBox);
        JButton start = new JButton("start");
        container.add(start);
        container.setLayout(new GridLayout(2, 2));
        this.pack();
        start.addActionListener(new Start());
    }


    private static class Start implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            List<LinkedTreeMap> data = new Database(selectBox.getSelectedItem().toString()).read();
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            for (LinkedTreeMap o : data) {
                Robot finalRobot = robot;
                Optional.ofNullable(o.get("x")).ifPresent(x->{
                    finalRobot.delay(500);
                    finalRobot.mouseMove(((Double)x).intValue(), ((Double)o.get("y")).intValue());
                    finalRobot.delay(500);
                    finalRobot.mousePress(InputEvent.BUTTON1_MASK);
                    finalRobot.mouseRelease(InputEvent.BUTTON1_MASK);
                });
                Optional.ofNullable(o.get("key")).ifPresent(key->{
                    finalRobot.delay(100);
                    try {
                        finalRobot.keyPress(new KeyEvents().getKey(key.toString()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

}
