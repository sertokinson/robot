package ru.sertok.robot.gui;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.AssertData;
import ru.sertok.robot.data.AssertResult;
import ru.sertok.robot.hook.KeyEvents;
import ru.sertok.robot.services.Database;
import ru.sertok.robot.services.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.sertok.robot.utils.Utils.filterFiles;

@Component
public class RobotWindow extends JFrame implements Window {

    @Autowired
    private Database database;

    public RobotWindow() throws HeadlessException {
        super("Robot");
    }

    @Override
    public JFrame getComponent() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 2));

        JLabel label = new JLabel("Выберите тест кейс: ");
        container.add(label);
        File dir = new File("database");
        List<File> files = Arrays.asList(dir.listFiles());
        JComboBox selectBox = new JComboBox(files.stream().map(f -> f.getName().split(".json")[0]).toArray());
        container.add(selectBox);


        JButton start = new JButton("start");
        JButton stop = new JButton("stop");
        container.add(start);
        container.add(stop);
        stop.addActionListener(e -> System.exit(1));
        start.addActionListener(actionEvent -> {
            String testCase = selectBox.getSelectedItem().toString();
            for (File file : filterFiles(testCase)) {
                database.delete(file);
            }
            List<LinkedTreeMap> data = database.read(testCase);
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < data.size(); i++) {
                LinkedTreeMap object = data.get(i);
                Object x = object.get("x");
                if (x != null) {
                    robot.delay(900);
                    robot.mouseMove(((Double) x).intValue(), ((Double) object.get("y")).intValue());
                    robot.delay(900);

                    if (i + 2 < data.size()) {
                        Object time = data.get(i + 2).get("time");
                        if (time != null) {
                            Double firstClick = (Double) object.get("time");
                            Double secondClick = (Double) time;
                            if (secondClick - firstClick < 400) {
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                i += 3;
                                continue;
                            }
                        }
                    }
                    if (i + 1 < data.size()) {
                        if (data.get(i + 1).get("time") != null) {
                            Double firstClick = (Double) object.get("time");
                            Double secondClick = (Double) data.get(i + 1).get("time");
                            if (secondClick - firstClick < 400) {
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                i++;
                                continue;
                            }
                        }
                    }
                    if (object.get("type").equals("pressed"))
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                    else robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
                Object key = object.get("key");
                if (key != null) {
                    robot.delay(100);
                    try {
                        int keyEvent = new KeyEvents().getKey(key.toString());
                        if (object.get("type").equals("pressed")) {
                            robot.keyPress(keyEvent);
                        } else robot.keyRelease(keyEvent);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            AssertResult result = assertation(filterFiles(testCase));

            JOptionPane.showMessageDialog(this,
                    result.getMessages(),
                    "Результат теста",
                    result.getType());
        });

        this.pack();

        return this;
    }

    private AssertResult assertation(List<File> files) {
        if (files.isEmpty()) {
            return new AssertResult(new String[]{"Произошла ошибка!", "рекомендуется перезаписать тест"}, JOptionPane.ERROR_MESSAGE);
        }
        for (File file : files) {
            AssertData assertData = database.read(file);
            String expected = assertData.getExpected();
            String actual = assertData.getActual();
            if (!expected.equals(actual)) {
                return new AssertResult(new String[]{"Значения не совпали", "Ожидаемый результат: " + expected, "Фактитческий результат: " + actual}, JOptionPane.ERROR_MESSAGE);
            }
        }
        return new AssertResult(new String[]{"SUCCESS!!!"}, JOptionPane.INFORMATION_MESSAGE);
    }
}
