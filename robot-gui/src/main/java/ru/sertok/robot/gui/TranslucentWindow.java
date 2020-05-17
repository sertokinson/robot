package ru.sertok.robot.gui;

import org.springframework.stereotype.Component;

@Component
public class TranslucentWindow {
    private Line top;
    private Line bottom;
    private Line right;
    private Line left;

    public TranslucentWindow() {
        top = new Line();
        bottom = new Line();
        right = new Line();
        left = new Line();
    }

    public void setSize(int x1, int y1, int x2, int y2) {
        top.setSize(x1, y1, x2, y1).horizontal().setLocation(x1, y1);
        bottom.setSize(x1, y2, x2, y2).horizontal().setLocation(x1, y2);
        left.setSize(x1, y1, x1, y2).vertical().setLocation(x1, y1);
        right.setSize(x2, y1, x2, y2).vertical().setLocation(x2, y1);
    }

    public void dispose() {
        top.dispose();
        bottom.dispose();
        right.dispose();
        left.dispose();
    }
}