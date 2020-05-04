package ru.sertok.robot.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ExecuteApp;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.Type;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RobotWindow {
    private final Database database;
    private final ExecuteApp executeApp;
    private final ScreenShot screenShot;
    private final LocalStorage localStorage;

    private JComboBox selectBox;

    public void create(Container container) {
        JLabel label = new JLabel("Выберите тест кейс: ");
        selectBox = new JComboBox(database.getAll().toArray());
        JButton start = new JButton("start");
        container.add(label);
        container.add(selectBox);
        container.add(start);
        start.addActionListener(actionEvent -> startAction(container));

    }

    public JComboBox getSelectBox() {
        return selectBox;
    }

    private void startAction(Container container) {
        String testCaseName = selectBox.getSelectedItem().toString();
        TestCase testCase = database.get(testCaseName);
        List<Object> data = testCase.getSteps();
        screenShot.setSize(new Point(testCase.getImage().getX(), testCase.getImage().getY()), new Dimension(testCase.getImage().getWidth(), testCase.getImage().getHeight()));
        localStorage.setImages(new ArrayList<>());
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        executeApp.execute(testCase.getUrl());
        robot.delay(900);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof Mouse) {
                Mouse mouse = (Mouse) data.get(i);
                if (mouse.getType() == Type.MOVED) {
                    robot.mouseMove(mouse.getX(), mouse.getY());
                } else if (mouse.getType() == Type.PRESSED) {
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                } else {
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
                if (mouse.isScreenshot())
                    screenShot.makeRobot();
                if (i + 1 < data.size()) {
                    if (data.get(i + 1) instanceof Mouse) {
                        robot.delay(((Mouse) data.get(i + 1)).getTime() - mouse.getTime());
                    }
                }
              /*  if (i + 2 < data.size()) {
                    if (data.get(i + 2) instanceof Mouse) {
                        int firstClick = mouse.getTime();
                        int secondClick = ((Mouse) data.get(i + 2)).getTime();
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
                    if (data.get(i + 1) instanceof Mouse) {
                        int firstClick = mouse.getTime();
                        int secondClick = ((Mouse) data.get(i + 1)).getTime();
                        if (secondClick - firstClick < 400) {
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            i++;
                            continue;
                        }
                    }
                }*/

            }
           /* if (data.get(i) instanceof Keyboard) {
                Keyboard keyboard = (Keyboard) data.get(i);
                robot.delay(200);
                try {
                    int keyEvent = new KeyEvents().getKey(keyboard.getKey());
                    if (keyboard.getType() == Type.PRESSED) {
                        robot.keyPress(keyEvent);
                    } else {
                        robot.keyRelease(keyEvent);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/
        }
        List<Image> images = database.getImages(testCaseName);
        List<ImageEntity> actualImages = localStorage.getImages();
        boolean success = true;
        for (int i = 0; i < images.size(); i++) {
            success = false;
            byte[] expectedImage = images.get(i).getExpectedImage();
            for (ImageEntity image : actualImages) {
                byte[] actualImage = image.getPhotoActual();
                if (compare(actualImage, expectedImage)) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                JOptionPane.showMessageDialog(container,
                        new String[]{"ERROR!!!" + i},
                        "Результат теста",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
        if (success) {
            JOptionPane.showMessageDialog(container,
                    new String[]{"SUCCESS!!!"},
                    "Результат теста",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        database.save(actualImages);
          /*  AssertResult result = assertation(Utils.filterFiles(testCase));

            JOptionPane.showMessageDialog(this,
                    result.getMessages(),
                    "Результат теста",
                    result.getType());*/
    }

    private boolean compare(byte[] actual, byte[] expected) {
        try {
            BufferedImage expectedImage = ImageIO.read(new ByteArrayInputStream(expected));
            BufferedImage actualImage = ImageIO.read(new ByteArrayInputStream(actual));
            if (actualImage.getWidth() != expectedImage.getWidth() || expectedImage.getHeight() != actualImage.getHeight()) {
                System.out.println("dimensions must be the same");
                return false;
            }
            int countIsNotIdentic = 0;
            for (int i = 0; i < actualImage.getWidth(); i++) {
                for (int j = 0; j < actualImage.getHeight(); j++) {
                    int actualRGB = actualImage.getRGB(i, j);
                    int expectedRGB = expectedImage.getRGB(i, j);
                    Color color1 = new Color((actualRGB >> 16) & 0xFF, (actualRGB >> 8) & 0xFF, (actualRGB) & 0xFF);
                    Color color2 = new Color((expectedRGB >> 16) & 0xFF, (expectedRGB >> 8) & 0xFF, (expectedRGB) & 0xFF);
                    if (!compareColor(color1, color2)) {
                        countIsNotIdentic++;
                    }
                }
            }
            return (countIsNotIdentic * 100) / (actualImage.getWidth() * actualImage.getHeight()) <= 10;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean compareColor(Color color1, Color color2) {
        if (Math.abs(color1.getRed() - color2.getRed()) > 10) {
            return false;
        }
        if (Math.abs(color1.getBlue() - color2.getBlue()) > 10) {
            return false;
        }
        return Math.abs(color1.getGreen() - color2.getGreen()) <= 10;

    }

   /* private AssertResult assertation(List<File> files) {
        if (files.isEmpty()) {
            return new AssertResult(new String[]{"Произошла ошибка!", "рекомендуется перезаписать тест"}, JOptionPane.ERROR_MESSAGE);
        }
        for (File file : files) {
            if (file.getName().contains("json")) {
                AssertData assertData = database.read(file);
                String expected = assertData.getExpected();
                String actual = assertData.getActual();
                if (!expected.equals(actual)) {
                    return new AssertResult(new String[]{"Значения не совпали", "Ожидаемый результат: " + expected, "Фактитческий результат: " + actual}, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return new AssertResult(new String[]{"SUCCESS!!!"}, JOptionPane.INFORMATION_MESSAGE);
    }*/
}
