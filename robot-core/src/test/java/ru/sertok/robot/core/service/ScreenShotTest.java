package ru.sertok.robot.core.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sertok.robot.data.ScreenshotSize;
import ru.sertok.robot.storage.LocalStorage;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ScreenShotTest {
    @Autowired
    private ScreenShot screenShot;
    @Autowired
    private LocalStorage localStorage;

    @Test
    public void setSize() {
        ScreenshotSize size = new ScreenshotSize(10, 10, 10, 10);
        screenShot.setSize(size);
        assertEquals(size, localStorage.getSize());
    }

    @After
    public void clear(){
        localStorage.invalidateLocalStorage();
    }
}