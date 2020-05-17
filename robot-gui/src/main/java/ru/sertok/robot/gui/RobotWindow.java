package ru.sertok.robot.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.request.RobotRequest;

import javax.swing.*;
import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RobotWindow {
    private JComboBox selectBox;

    void getComponent(Container container) {
        JLabel label = new JLabel("Выберите тест кейс: ");
        selectBox = new JComboBox();
        JButton start = new JButton("start");
        container.add(label);
        container.add(selectBox);
        container.add(start);
        start.addActionListener(actionEvent -> {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<RobotRequest> request = new HttpEntity<>(new RobotRequest(selectBox.getSelectedItem().toString()));
            Status status = restTemplate.postForObject("http://localhost:8080/autotest/robot/start", request, Status.class);
            if (Status.TEST_ERROR == status) {
                JOptionPane.showMessageDialog(container,
                        new String[]{"ERROR!!!"},
                        "Результат теста",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(container,
                        new String[]{"SUCCESS!!!"},
                        "Результат теста",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    JComboBox getSelectBox() {
        return selectBox;
    }

}
