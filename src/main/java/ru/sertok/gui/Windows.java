package ru.sertok.gui;

public enum Windows {
    RECORD {
        public String getTestCase() {
            return RecordWindow.input.getText();
        }
    },
    ROBOT{
        public String getTestCase() {
            return RobotWindow.testCase;
        }
    };
    public abstract String getTestCase();
}
