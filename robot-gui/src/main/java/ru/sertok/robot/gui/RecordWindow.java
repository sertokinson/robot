package ru.sertok.robot.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sertok.robot.gui.data.Input;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.request.ScreenShotRequest;
import ru.sertok.robot.request.SettingsRequest;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;


@Component
@Profile("local-gui")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordWindow extends JFrame {
    private final RobotWindow robotWindow;

    private Input input = new Input();

    @PostConstruct
    public void getComponent() {
        createWindow();
        textField("Введите название тест кейса: ");
        inputTestCase();
        textField("url: ");
        inputUrl();
        Container container = this.getContentPane();
        JButton start = new JButton("start");
        JButton stop = new JButton("stop");
        JButton crop = crop();
        container.add(start);
        container.add(stop);
        container.add(crop);
        crop.addActionListener(action -> {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject("http://localhost:8080/autotest/screenshot/crop/", new HttpEntity<>(new ScreenShotRequest()), Response.class);
        });
        start.addActionListener(actionEvent -> {
            // Целевое решение
            // HttpEntity<RecordRequest> request = new HttpEntity<>(new RecordRequest(input.getTestCase().getText(), input.getUrl().getText()));
            // Для быстрого тестирования от сюда
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject("http://localhost:8080/autotest/settings/pathToApp/", new HttpEntity<>(new SettingsRequest("/Applications/Google Chrome.app")), Response.class);
            HttpEntity<RecordRequest> request = new HttpEntity<>(new RecordRequest("test", "file:///Users/sergejstahanov/Desktop/robot_test/index.html"));
            // до сюда
            restTemplate.postForObject("http://localhost:8080/autotest/record/start", request, RecordRequest.class);
        });
        stop.addActionListener(actionEvent -> {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForEntity("http://localhost:8080/autotest/record/stop", String.class);
            this.pack();
        });

        robotWindow.getComponent(this.getContentPane());
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setVisible(true);
        // Для быстрого тестирования
        selectBox();
        this.pack();
    }

    private void selectBox() {
        JComboBox selectBox = robotWindow.getSelectBox();
        selectBox.addItem("test");
        selectBox.repaint();
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


    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

}
