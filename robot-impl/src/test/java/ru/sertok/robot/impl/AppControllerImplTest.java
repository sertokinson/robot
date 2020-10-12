package ru.sertok.robot.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppControllerImplTest {
    private final AppControllerImpl appController = new AppControllerImpl();

    @Test
    public void ping() {
        assertEquals("PONG", appController.ping().getResult());
    }

    @Test
    public void pathToLog() {
        assertEquals(System.getProperty("java.io.tmpdir") + "robot.log", appController.pathToLog().getResult());
    }

    @Test
    public void version() {
        assertEquals("0.25-alpha", appController.version().getResult());
    }
}