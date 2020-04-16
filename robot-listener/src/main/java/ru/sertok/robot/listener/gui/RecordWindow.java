package ru.sertok.robot.listener.gui;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.api.ScreenShot;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.listener.hook.EventListener;
import ru.sertok.robot.api.Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Component
public class RecordWindow extends JFrame implements Window {
    public static long startTime;
    public static boolean activeCrop = false;

    @Autowired
    private Database database;
    @Autowired
    private EventListener eventListener;
    @Autowired
    private RecordButtons recordButtons;
    @Autowired
    private ScreenShot screenShot;

    public RecordWindow() throws HeadlessException {
        super("Recorder");
        startTime = System.currentTimeMillis();
    }

    public RecordWindow getComponent() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 2));
        JTextField input = new JTextField();
        JLabel label = new JLabel("Введите название тест кейса: ");
        container.add(label);
        container.add(input);
        JButton start = new JButton("start");
        container.add(start);
        JButton stop = new JButton("stop");
        JButton crop = new JButton();
        try {
            Image img = ImageIO.read(getClass().getClassLoader().getResource("crop.png"));
            crop.setIcon(new ImageIcon(getScaledImage(img, 10, 10)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.add(crop);
        container.add(stop);
        this.pack();

        crop.addActionListener(actionEvent -> activeCrop = true);
        start.addActionListener(actionEvent -> {
            recordButtons.getStartButton().addActionListener(action -> screenShot.make(input.getText() + "/robot"));

            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native ru.sertok.hook.");
                System.err.println(ex.getMessage());
                System.exit(1);
            }
            // Add the appropriate listeners.
            GlobalScreen.addNativeMouseListener(eventListener);
            GlobalScreen.addNativeMouseMotionListener(eventListener);
            GlobalScreen.addNativeKeyListener(eventListener);
        });

        stop.addActionListener(actionEvent -> {
            String testCase = input.getText();

            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
            database.writeJson(EventListener.steps, testCase, testCase);
            System.exit(1);
        });
        return this;
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
