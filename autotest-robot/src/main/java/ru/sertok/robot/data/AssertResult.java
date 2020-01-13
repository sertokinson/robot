package ru.sertok.robot.data;

public class AssertResult {
    private String[] messages;
    private int type;

    public String[] getMessages() {
        return messages;
    }

    public int getType() {
        return type;
    }

    public AssertResult(String[] messages, int type) {
        this.messages = messages;
        this.type = type;
    }
}
