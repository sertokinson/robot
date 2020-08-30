package ru.sertok.robot.core.hook;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.enumerate.Type;
import ru.sertok.robot.data.enumerate.TypePressed;
import ru.sertok.robot.data.storage.LocalStorage;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EventListenerTest {
    @Autowired
    private EventListener eventListener;
    @Autowired
    private LocalStorage localStorage;

    @Before
    public void init() {
        localStorage.setStartTime(-10L);
    }

    @Test
    public void nativeKeyPressed() {
        eventListener.nativeKeyPressed(new NativeKeyEvent(1, 1L, 2, 2, 3, '1'));
        assertEquals(Type.PRESSED, localStorage.getSteps().get(0).getType());
    }

    @Test
    public void nativeKeyReleased() {
        eventListener.nativeKeyReleased(new NativeKeyEvent(1, 1L, 2, 2, 3, '1'));
        assertEquals(Type.RELEASED, localStorage.getSteps().get(0).getType());
    }

    @Test
    public void nativeMousePressed_UNKNOWN() {
        eventListener.nativeMousePressed(new NativeMouseEvent(1, 1L, 1, 1, 1, 1));
        Mouse mouse = (Mouse) localStorage.getSteps().get(0);
        assertEquals(Type.PRESSED, mouse.getType());
        assertEquals(TypePressed.UNKNOWN, mouse.getTypePressed());
        assertEquals(1, mouse.getX());
        assertEquals(1, mouse.getY());
        assertEquals(0, mouse.getWheel());
        assertEquals(1, mouse.getCount());
    }

    @Test
    public void nativeMousePressed_LEFT() {
        eventListener.nativeMousePressed(new NativeMouseEvent(1, 1L, 1, 1, 1, 1, 1));
        assertEquals(TypePressed.LEFT, ((Mouse) localStorage.getSteps().get(0)).getTypePressed());
    }

    @Test
    public void nativeMousePressed_RIGHT() {
        eventListener.nativeMousePressed(new NativeMouseEvent(1, 1L, 1, 1, 1, 1, 2));
        assertEquals(TypePressed.RIGHT, ((Mouse) localStorage.getSteps().get(0)).getTypePressed());
    }

    @Test
    public void nativeMouseReleased() {
        eventListener.nativeMouseReleased(new NativeMouseEvent(1, 1L, 1, 1, 1, 1, 2));
        assertEquals(Type.RELEASED, localStorage.getSteps().get(0).getType());
    }

    @Test
    public void nativeMouseDragged() {
        eventListener.nativeMouseDragged(new NativeMouseEvent(1, 1L, 1, 1, 1, 1, 2));
        assertEquals(Type.MOVED, localStorage.getSteps().get(0).getType());
    }

    @Test
    public void nativeMouseWheelMoved() {
        eventListener.nativeMouseWheelMoved(new NativeMouseWheelEvent(1, 1L, 1, 1, 1, 1, 1, 1, 1));
        Mouse mouse = (Mouse) localStorage.getSteps().get(0);
        assertEquals(Type.WHEEL, mouse.getType());
        assertEquals(1, mouse.getWheel());
    }

    @After
    public void clear() {
        localStorage.invalidateLocalStorage();
    }

}