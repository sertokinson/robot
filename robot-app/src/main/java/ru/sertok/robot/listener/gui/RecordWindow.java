package ru.sertok.robot.listener.gui;

import lombok.RequiredArgsConstructor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ExecuteApp;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.entity.ImageEntity;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.gui.RobotWindow;
import ru.sertok.robot.listener.data.Input;
import ru.sertok.robot.listener.hook.EventListener;
import ru.sertok.robot.api.Window;
import ru.sertok.robot.repository.TestCaseRepository;
import ru.sertok.robot.storage.LocalStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordWindow extends JFrame implements Window {
    private final Database database;
    private final EventListener eventListener;
    private final RecordButtons recordButtons;
    private final ScreenShot screenShot;
    private final RobotWindow robotWindow;
    private final LocalStorage localStorage;
    private final ExecuteApp executeApp;
    private final TestCaseRepository testCaseRepository;

    private Input input = new Input();

    private void localImageOut() {
        TestCaseEntity testCaseEntity = testCaseRepository.findByName("test").get();
        List<ImageEntity> images = testCaseEntity.getImages();
        images.sort(Comparator.comparingInt(ImageEntity::getPosition));
        for (int i = 0; i < images.size(); i++) {
            InputStream in = new ByteArrayInputStream(images.get(i).getPhotoActual());
            try {
                database.writePng(ImageIO.read(in), "test", "test" + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public RecordWindow getComponent() {
        createWindow();
        localImageOut();
        textField("Введите название тест кейса: ");
        inputTestCase();
        textField("url: ");
        inputUrl();
        threeRow();

        robotWindow.create(this.getContentPane());
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.pack();
        localStorage.setSize(this.getSize());
        localStorage.setLocation(this.getLocation());
        return this;
    }

    private void createWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
    }

    private void textField(String text) {
        Container container = this.getContentPane();
        JLabel label = new JLabel(text);
        container.add(label);
    }

    private void inputTestCase() {
        Container container = this.getContentPane();
        JTextField testCase = new JTextField();
        testCase.setPreferredSize(new Dimension(200, 24));
        container.add(testCase);
        input.setTestCase(testCase);
    }

    private void inputUrl() {
        Container container = this.getContentPane();
        JTextField url = new JTextField();
        url.setPreferredSize(new Dimension(200, 24));
        container.add(url);
        input.setUrl(url);
    }

    private void threeRow() {
        Container container = this.getContentPane();
        JButton start = new JButton("start");
        JButton stop = new JButton("stop");
        JButton crop = crop();
        container.add(start);
        container.add(stop);
        container.add(crop);
        crop.addActionListener(actionEvent -> localStorage.setActiveCrop(true));
        start.addActionListener(actionEvent -> startAction());
        stop.addActionListener(actionEvent -> stopAction());
    }

    private JButton crop() {
        JButton crop = new JButton();
        try {
            Image img = ImageIO.read(getClass().getClassLoader().getResource("crop.png"));
            crop.setIcon(new ImageIcon(getScaledImage(img, 10, 10)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crop;
    }

    private void startAction() {
        localStorage.setStartTime(System.currentTimeMillis());
        String url = input.getUrl().getText();
        String testCase = input.getTestCase().getText();
        executeApp.execute(url);
        recordButtons.getStartButton().addActionListener(action -> {
            localStorage.getSteps().add(screenShot.getImage());
            screenShot.make(testCase);
        });

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native ru.sertok.hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeMouseListener(eventListener);
        GlobalScreen.addNativeMouseMotionListener(eventListener);
        GlobalScreen.addNativeKeyListener(eventListener);
    }

    private void stopAction() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        database.save(TestCase.builder()
                .steps(localStorage.getSteps())
                .name(input.getTestCase().getText())
                .url(input.getUrl().getText())
                .build()
        );
    }


    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }


}
