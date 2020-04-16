package ru.sertok.robot.assertation.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.AssertData;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.api.Window;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class AssertComponent extends JFrame implements Window {
    private JTextField input = new JTextField();

    @Autowired
    private Database database;

    private JButton add = new JButton("+");
    private JTextField expected = new JTextField();
    private JTextField actual = new JTextField();

    @Override
    public JFrame getComponent() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2));
        JLabel label = new JLabel("Введите название тест кейса: ");
        container.add(label);
        container.add(input);
        container.add(new JLabel());
        container.add(new JLabel("Ожидаемое значение: "));
        container.add(new JLabel("Фактический результат: "));
        container.add(new JLabel());
        container.add(expected);
        container.add(actual);
        container.add(add);
        this.pack();
        add.addActionListener(new Action(this));
        Rectangle desktopBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        final int width = this.getWidth();
        final int height = this.getHeight();
        this.setBounds(
                desktopBounds.x + desktopBounds.width - width,
                desktopBounds.y,
                width,
                height
        );

        return this;
    }

    private class Action implements ActionListener{
        JFrame jFrame;
        int count = 0;

        Action(JFrame jFrame) {
            this.jFrame = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            database.writeJson(new AssertData(expected.getText(), actual.getText()),
                     input.getText(),  "assert" + (count++));

            Container container = jFrame.getContentPane();
            container.remove(add);
            container.add(new JLabel());
            container.add(expected = new JTextField());
            container.add(actual = new JTextField());
            container.add(add);
            container.setLayout(new GridLayout(0, 3));
            jFrame.pack();
        }
    }
}
