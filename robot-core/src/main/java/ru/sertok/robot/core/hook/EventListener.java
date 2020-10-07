package ru.sertok.robot.core.hook;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.core.storage.LocalStorage;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListener implements NativeMouseInputListener, NativeKeyListener {
    private final AppService appService;
    private final LocalStorage localStorage;
    private final KeyEvents keyEvents;

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        ((JavascriptExecutor) appService.getDriver())
                .executeScript("(function() { " +
                        "var element;" +
                        "window.addEventListener('click', function(e) {" +
                        "element = document.elementFromPoint(e.clientX, e.clientY);" +
                        "}, true);" +
                        "window._getElement = function() {" +
                        "  return element;" +
                        " };" +
                        "})(); ");
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        WebDriver driver = appService.getDriver();
        Optional.ofNullable(((JavascriptExecutor) driver).executeScript("return window._getElement();"))
                .ifPresent(el -> {
                    WebElement webElement = (WebElement) el;
                    localStorage.getSteps().add(new Mouse(getElementXPath(driver, webElement)));
                    localStorage.getImages().add(webElement.getScreenshotAs(OutputType.BASE64));
                    new Actions(driver).moveToElement(webElement).click().perform();
                });
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
        if (keyEvents.getKey(keyText) != 0)
            localStorage.getSteps().add(new Keyboard(keyText));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    private String getElementXPath(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver).executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();", element);
    }
}