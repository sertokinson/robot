package ru.sertok.robot.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.request.RobotRequest;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RobotControllerImplTest {
    @Autowired
    private RobotControllerImpl robotController;

    @Test
    public void start() {
        assertEquals(Status.ERROR, robotController.start(new RobotRequest()).getStatus());
    }

    @Test
    public void delete() {
        assertEquals(Status.SUCCESS, robotController.delete(new RobotRequest()).getStatus());
    }

    @Test
    public void getAll() {
        assertEquals(Status.SUCCESS, robotController.getAll().getStatus());
    }
}