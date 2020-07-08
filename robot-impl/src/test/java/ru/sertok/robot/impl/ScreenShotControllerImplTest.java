package ru.sertok.robot.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sertok.robot.data.ScreenshotSize;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.request.ScreenShotRequest;
import ru.sertok.robot.storage.LocalStorage;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ScreenShotControllerImplTest {
    @Autowired
    private ScreenShotControllerImpl screenShotController;
    @Autowired
    private LocalStorage localStorage;

    @Before
    public void init() {
        localStorage.setStartTime(System.currentTimeMillis());
    }

    @Test
    public void start() {
        assertEquals(Status.SUCCESS, screenShotController.start(new ScreenShotRequest(new ScreenshotSize())).getStatus());
        assertTrue(localStorage.isScreenshotStart());
    }

    @Test
    public void stop() {
        assertEquals(Status.SUCCESS, screenShotController.stop().getStatus());
        assertFalse(localStorage.isScreenshotStart());
    }

    @Test
    public void crop() {
        assertEquals(Status.SUCCESS, screenShotController.crop().getStatus());
        assertTrue(localStorage.isActiveCrop());
    }

    @After
    public void clear() {
        localStorage.invalidateLocalStorage();
    }
}