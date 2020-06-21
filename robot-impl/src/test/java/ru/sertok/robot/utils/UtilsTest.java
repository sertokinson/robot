package ru.sertok.robot.utils;

import org.junit.Test;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void delete_1() {
        List<BaseData> baseData = new ArrayList<>();
        Utils.deleteLastMousePressed(baseData);
        assertEquals(0, baseData.size());
    }

    @Test
    public void delete_2() {
        List<BaseData> baseData = new ArrayList<>();
        baseData.add(Mouse.builder().build());
        Utils.deleteLastMousePressed(baseData);
        assertEquals(1, baseData.size());
    }

    @Test
    public void delete_3() {
        List<BaseData> baseData = new ArrayList<>();
        baseData.add(Mouse.builder().type(Type.PRESSED).build());
        baseData.add(Mouse.builder().type(Type.RELEASED).build());
        Utils.deleteLastMousePressed(baseData);
        assertEquals(0, baseData.size());
    }

    @Test
    public void delete_4() {
        List<BaseData> baseData = new ArrayList<>();
        baseData.add(Mouse.builder().type(Type.PRESSED).build());
        baseData.add(Mouse.builder().type(Type.MOVED).build());
        baseData.add(Mouse.builder().type(Type.RELEASED).build());
        Utils.deleteLastMousePressed(baseData);
        assertEquals(1, baseData.size());
    }

    @Test
    public void delete_5() {
        List<BaseData> baseData = new ArrayList<>();
        baseData.add(Keyboard.builder().build());
        baseData.add(Mouse.builder().type(Type.PRESSED).build());
        baseData.add(Mouse.builder().type(Type.MOVED).build());
        baseData.add(Mouse.builder().type(Type.RELEASED).build());
        baseData.add(Keyboard.builder().build());
        Utils.deleteLastMousePressed(baseData);
        assertEquals(3, baseData.size());
    }

}