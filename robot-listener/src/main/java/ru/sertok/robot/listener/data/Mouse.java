package ru.sertok.robot.listener.data;

public class Mouse {
    private String type;
    private int x;
    private int y;
    private double time;

    public Mouse(String type, int x, int y, double time) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
