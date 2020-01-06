package ru.sertok.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.data.AssertData;
import ru.sertok.services.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class AssertComponent {

    @Autowired
    private Database database;

    private JButton add = new JButton("+");
    private JTextField expected = new JTextField();
    private JTextField actual = new JTextField();


    void setComponent(Windows windows, Container container, JFrame frame) {
        container.add(new JLabel("Ожидаемое значение: "));
        container.add(new JLabel("Фактический результат: "));
        container.add(new JLabel());
        container.add(expected);
        container.add(actual);
        container.add(add);
        add.addActionListener(new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                database.write(new AssertData(expected.getText(), actual.getText()),
                        "database/" + windows.getTestCase(), "/" + "assert" + (count++) + ".json");

                container.remove(add);
                container.add(new JLabel());
                container.add(expected = new JTextField());
                container.add(actual = new JTextField());
                container.add(add);
                container.setLayout(new GridLayout(0, 3));
                frame.pack();
            }
        });
    }
}
