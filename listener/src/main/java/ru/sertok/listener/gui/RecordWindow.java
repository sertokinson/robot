package ru.sertok.listener.gui;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.listener.hook.EventListener;
import ru.sertok.robot.services.Database;
import ru.sertok.robot.services.Window;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static ru.sertok.robot.utils.Utils.filterFiles;

@Component
public class RecordWindow extends JFrame implements Window {
    public static long startTime;

    @Autowired
    private Database database;

    public RecordWindow() throws HeadlessException {
        super("Recorder");
        startTime = System.currentTimeMillis();
    }

    public RecordWindow getComponent(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2,2));
        JTextField input = new JTextField();
        JLabel label = new JLabel("Введите название тест кейса: ");
        container.add(label);
        container.add(input);
        JButton start = new JButton("start");
        container.add(start);
        JButton stop = new JButton("stop");
        container.add(stop);
        this.pack();
        start.addActionListener(actionEvent -> {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native ru.sertok.hook.");
                System.err.println(ex.getMessage());
                System.exit(1);
            }

            // Construct the example object.
            EventListener example = new EventListener();

            // Add the appropriate listeners.
            GlobalScreen.addNativeMouseListener(example);
            GlobalScreen.addNativeKeyListener(example);
        });

        stop.addActionListener(actionEvent -> {
            String testCase = input.getText();
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
            database.write(EventListener.steps, testCase, "/" + testCase + ".json");
            System.exit(1);
        });

        return this;
    }
}
