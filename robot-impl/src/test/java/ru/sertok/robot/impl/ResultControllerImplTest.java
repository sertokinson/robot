package ru.sertok.robot.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.response.ResultResponse;
import ru.sertok.robot.service.TestCaseService;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultControllerImplTest {
    @InjectMocks
    private ResultControllerImpl resultController;
    @Mock
    private TestCaseService testCaseService;

    @Before
    public void init() {
        when(testCaseService.getTestCaseEntity(any())).thenReturn(TestCaseEntity.builder().images(new ArrayList<>()).build());
    }

    @Test
    public void getAll() {
        assertEquals(Status.ERROR, resultController.toPath("").getStatus());
    }

    @Test
    public void get() {
        assertEquals(Status.SUCCESS, resultController.get("").getStatus());
    }

    @Test
    public void getErrors() {
        assertEquals(Status.ERROR, resultController.errors("").getStatus());
    }
}