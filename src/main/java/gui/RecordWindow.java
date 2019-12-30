package gui;

import com.google.gson.Gson;
import hook.Database;
import hook.EventListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordWindow extends JFrame {
    private static JTextField input = new JTextField(15);
    private static Database database ;

    public RecordWindow() throws HeadlessException {
        super("Recorder");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        JLabel label = new JLabel("Введите название тест кейса: ");
        container.add(label);
        container.add(input);
        JButton start = new JButton("start");
        container.add(start);
        JButton stop = new JButton("stop");
        container.add(stop);
        container.setLayout(new GridLayout(2,2));
        this.pack();
        start.addActionListener(new Start());
        stop.addActionListener(new Stop());
    }



    private static class Start implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            database = new Database(RecordWindow.input.getText());
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());

                System.exit(1);
            }

            // Construct the example object.
            EventListener example = new EventListener();

            // Add the appropriate listeners.
            GlobalScreen.addNativeMouseListener(example);
            GlobalScreen.addNativeKeyListener(example);
        }
    }

    private static class Stop implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
            database.write(new Gson().toJson(EventListener.steps));
            System.exit(1);
        }
    }
}
