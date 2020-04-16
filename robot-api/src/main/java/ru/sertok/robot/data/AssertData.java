package ru.sertok.robot.data;

public class AssertData {
    private String expected;
    private String actual;

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }

    public AssertData(String expected, String actual) {
        this.expected = expected;
        this.actual = actual;
    }
}
