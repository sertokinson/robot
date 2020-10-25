package ru.sertok.robot.core.hook;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.core.storage.LocalStorage;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    private final AppService appService;
    private final LocalStorage localStorage;
    private final KeyEvents keyEvents;

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        WebDriver driver = appService.getDriver();
        Optional.ofNullable(((JavascriptExecutor) driver).executeScript("return document.elementFromPoint(" + e.getX() + "," + e.getY() + ")"))
                .ifPresent(el -> localStorage.getSteps().add(new Mouse(getElementXPath(driver, (WebElement) el))));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {

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

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    @SneakyThrows
    public void nativeKeyReleased(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        WebDriver driver = appService.getDriver();
        Optional.ofNullable(((JavascriptExecutor) driver).executeScript("return document.activeElement;"))
                .ifPresent(el -> localStorage.getSteps()
                        .add(new Keyboard(keyEvents.getKey(keyText), getElementXPath(driver, (WebElement) el)))
                );
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    private String getElementXPath(WebDriver driver, WebElement element) {
        String text = element.getText();
        String xpath = (String) ((JavascriptExecutor) driver).executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();", element);
        if (text.isEmpty())
            return xpath;
        try {
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
            if (elements.size() == 1)
                return "//*[contains(text(),'" + text + "')]";
            return xpath;
        } catch (Exception e) {
            return xpath;
        }
    }
}