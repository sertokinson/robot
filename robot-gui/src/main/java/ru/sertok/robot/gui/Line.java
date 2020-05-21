package ru.sertok.robot.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

class Line extends JFrame {
    private Component component;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    Line() throws HeadlessException {
        super("Line");
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
    }

    Line setSize(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        component = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.gray);
                Shape line = new Line2D.Double(x1, y1, x2, y2);
                g2.draw(line);
            }
        };
        return this;
    }

    Line horizontal() {
        component.setPreferredSize(new Dimension(x2 - x1, 0));
        create();
        return this;
    }

    Line vertical() {
        component.setPreferredSize(new Dimension(0, y2 - y1));
        create();
        return this;
    }

    private void create() {
        this.getContentPane().add(component);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setOpacity(0.4f);
    }
}
