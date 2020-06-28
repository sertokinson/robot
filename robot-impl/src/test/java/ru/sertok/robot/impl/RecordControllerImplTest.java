package ru.sertok.robot.impl;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sertok.robot.data.TestCase;
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

    @Test
    public void start() {
        assertEquals(Status.SUCCESS, recordController.start(new RecordRequest(
                "test",
                true,
                "/Applications/Google Chrome.app",
                "https://www.google.com/",
                "Описание теста"
        )).getStatus());
        assertEquals(TestCase.builder()
                        .testCaseName("test")
                        .description("Описание теста")
                        .pathToApp("/Applications/Google Chrome.app")
                        .appName("Google Chrome.app")
                        .url("https://www.google.com/")
                        .isBrowser(true)
                        .build(),
                localStorage.getTestCase());
    }

    @Test
    public void error() {
        assertEquals(Status.ERROR, recordController.start(
                RecordRequest.builder()
                        .isBrowser(true)
                        .path("/Applications/Google Chrome.app")
                        .url("https://www.google.com/")
                        .description("Описание теста")
                        .build()
        ).getStatus());
        assertEquals(TestCase.builder()
                        .description("Описание теста")
                        .pathToApp("/Applications/Google Chrome.app")
                        .appName("Google Chrome.app")
                        .url("https://www.google.com/")
                        .isBrowser(true)
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
    public void clear(){
        localStorage.invalidateLocalStorage();
    }
}