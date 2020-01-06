package ru.sertok.gui;

import ru.sertok.hook.EventListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.services.Database;
import ru.sertok.services.Window;

import javax.swing.*;
import java.awt.*;

@Component
public class RecordWindow extends JFrame implements Window {
    static JTextField input = new JTextField();

    @Autowired
    private Database database;
    @Autowired
    private AssertComponent assertComponent;

    public RecordWindow() throws HeadlessException {
        super("Recorder");
    }

    public RecordWindow getComponent(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4,3));
        JLabel label = new JLabel("Введите название тест кейса: ");
        container.add(label);
        container.add(input);
        container.add(new JLabel());
        JButton start = new JButton("start");
        container.add(start);
        JButton stop = new JButton("stop");
        container.add(stop);
        container.add(new JLabel());
        assertComponent.setComponent(Windows.RECORD,container,this);
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
            String testCase = RecordWindow.input.getText();
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
            database.write(EventListener.steps,"database/" + testCase, "/" + testCase + ".json");
            System.exit(1);
        });

        return this;
    }
}
