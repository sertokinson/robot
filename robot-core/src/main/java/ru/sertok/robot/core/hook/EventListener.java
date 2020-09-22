package ru.sertok.robot.core.hook;

import lombok.RequiredArgsConstructor;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.openqa.selenium.OutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.core.storage.LocalStorage;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener {
    private final AppService appService;
    private final LocalStorage localStorage;

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        Optional.ofNullable(appService.getWebElement()).ifPresent(
                webElement -> localStorage.getImages().add(webElement.getScreenshotAs(OutputType.BASE64))
        );
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }
}