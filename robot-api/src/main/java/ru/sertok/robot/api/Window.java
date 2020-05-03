package ru.sertok.robot.api;
import javax.swing.*;
import java.io.IOException;

public interface Window {
    JFrame getComponent() throws IOException;
}
