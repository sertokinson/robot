package ru.sertok.gui;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.data.AssertData;
import ru.sertok.hook.KeyEvents;
import ru.sertok.services.Database;
import ru.sertok.services.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class RobotWindow extends JFrame implements Window {
    static String testCase;

    @Autowired
    private AssertComponent assertComponent;
    @Autowired
    private Database database;

    public RobotWindow() throws HeadlessException {
        super("Robot");
    }

    @Override
    public JFrame getComponent() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 3));

        JLabel label = new JLabel("Выберите тест кейс: ");
        container.add(label);
        File dir = new File("database");
        List<File> files = Arrays.asList(dir.listFiles());
        JComboBox selectBox = new JComboBox(files.stream().map(f -> f.getName().split(".json")[0]).toArray());
        container.add(selectBox);
        JButton start = new JButton("start");
        container.add(start);
        start.addActionListener(actionEvent -> {
            List<LinkedTreeMap> data = database.read("database/" + testCase + "/" + testCase + ".json");
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            for (LinkedTreeMap o : data) {
                Robot finalRobot = robot;
                Optional.ofNullable(o.get("x")).ifPresent(x -> {
                    finalRobot.delay(500);
                    finalRobot.mouseMove(((Double) x).intValue(), ((Double) o.get("y")).intValue());
                    finalRobot.delay(500);
                    finalRobot.mousePress(InputEvent.BUTTON1_MASK);
                    finalRobot.mouseRelease(InputEvent.BUTTON1_MASK);
                    finalRobot.mousePress(InputEvent.BUTTON1_MASK);
                    finalRobot.mouseRelease(InputEvent.BUTTON1_MASK);
                });
                Optional.ofNullable(o.get("key")).ifPresent(key -> {
                    finalRobot.delay(100);
                    try {
                        finalRobot.keyPress(new KeyEvents().getKey(key.toString()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }

            JOptionPane.showMessageDialog(this,
                    assertation(Arrays.asList(new File("database/" + testCase).listFiles())),
                    "Результат теста",
                    JOptionPane.ERROR_MESSAGE);
        });
        testCase = selectBox.getSelectedItem().toString();

        container.add(new JLabel());
        container.add(new JLabel());
        container.add(new JLabel());
        assertComponent.setComponent(Windows.ROBOT, container, this);

        this.pack();

        return this;
    }

    private String[] assertation(List<File> files) {
        for (File file : files) {
            if (!file.getName().equals(testCase + ".json")) {
                AssertData assertData = database.read(file);
                    if (!assertData.expected.equals(assertData.actual)) {
                        return new String[]{"Значения не совпали","Ожидаемый результат: " + assertData.expected,"Фактитческий результат: " + assertData.actual};
                }
            }
        }
        return new String[]{"SUCCESS!!!"};
    }
}
