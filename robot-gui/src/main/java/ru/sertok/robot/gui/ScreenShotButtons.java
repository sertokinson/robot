package ru.sertok.robot.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sertok.robot.request.ScreenShotRequest;
import ru.sertok.robot.storage.LocalStorage;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.ws.rs.core.Response;
import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenShotButtons extends JFrame {
    private final TranslucentWindow tw;
    private final LocalStorage localStorage;

    @PostConstruct
    public void getComponent() {
        setLayout(new GridBagLayout());
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 1));
        JButton start = new JButton("start");
        start.addActionListener(action -> {
            RestTemplate restTemplate = new RestTemplate();
            ru.sertok.robot.data.Image image = localStorage.getImage();
            restTemplate.postForObject("http://localhost:8080/autotest/screenshot/start/", new HttpEntity<>(new ScreenShotRequest(
                    image.getX(),
                    image.getY(),
                    image.getWidth(),
                    image.getHeight()
            )), Response.class);
        });
        JButton stop = new JButton("stop");
        stop.addActionListener(action -> {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject("http://localhost:8080/autotest/screenshot/stop/", new HttpEntity<>(new ScreenShotRequest()), Response.class);
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
