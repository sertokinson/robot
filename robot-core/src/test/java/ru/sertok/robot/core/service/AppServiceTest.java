package ru.sertok.robot.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.service.SettingsService;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppServiceTest {
    @InjectMocks
    private AppService appService;
    @Mock
    private SettingsService settingsService;

    @Test
    public void error_pathToApp() {
        assertEquals(Status.ERROR, appService.execute(TestCase.builder().build()));
    }

    @Test
    public void error() {
        when(settingsService.getPathToApp(any())).thenReturn("path");
        assertEquals(Status.ERROR, appService.execute(TestCase.builder().build()));
    }
}