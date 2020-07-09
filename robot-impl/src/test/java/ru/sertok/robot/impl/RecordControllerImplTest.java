package ru.sertok.robot.impl;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Platform;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.storage.LocalStorage;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RecordControllerImplTest {
    @Autowired
    private RecordControllerImpl recordController;
    @Autowired
    private LocalStorage localStorage;
    private final String path = getPath();
    private final String appName = getAppName();

    @Test
    public void start() {
        assertEquals(Status.SUCCESS, recordController.start(RecordRequest.builder()
                .testCaseName("test")
                .folderName("folder")
                .platform("WEB")
                .path(path)
                .url("https://www.google.com/")
                .description("Описание теста")
                .build()
        ).getStatus());
        assertEquals(TestCase.builder()
                        .testCaseName("test")
                        .folderName("folder")
                        .description("Описание теста")
                        .path(path)
                        .appName(appName)
                        .url("https://www.google.com/")
                        .platform(Platform.WEB)
                        .build(),
                localStorage.getTestCase());
    }

    @Test
    public void error() {
        assertEquals(Status.ERROR, recordController.start(
                RecordRequest.builder()
                        .platform("WEB")
                        .path(path)
                        .url("https://www.google.com/")
                        .description("Описание теста")
                        .build()
        ).getStatus());
        assertEquals(TestCase.builder()
                        .platform(Platform.WEB)
                        .path(path)
                        .appName(appName)
                        .url("https://www.google.com/")
                        .description("Описание теста")
                        .build(),
                localStorage.getTestCase());
    }

    @Test
    public void stop() {
        assertEquals(Status.SUCCESS, recordController.stop("").getStatus());
    }

    @Test
    public void exit() {
        assertEquals(Status.SUCCESS, recordController.exit().getStatus());
    }

    @After
    public void clear() {
        localStorage.invalidateLocalStorage();
    }

    private String getPath() {
        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            return "/Applications/Google Chrome.app";
        return "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
    }

    private String getAppName() {
        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            return "Google Chrome.app";
        return "chrome.exe";
    }
}