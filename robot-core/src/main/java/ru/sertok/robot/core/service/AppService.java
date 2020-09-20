package ru.sertok.robot.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.core.storage.LocalStorage;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.data.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppService {
    private final LocalStorage localStorage;

    private WebDriver driver;
    private WebElement webElement;

    public void execute(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        driver = new ChromeDriver(options);
        driver.get(url);
        ((JavascriptExecutor) driver)
                .executeScript("(function() { " +
                        " var events = [];" +
                        " var event;" +
                        " window.addEventListener('click', function(e) {" +
                        "event = {type: 'MOUSE', element: document.elementFromPoint(e.clientX, e.clientY)};" +
                        " events.push(event);" +
                        "}, true);" +
                        " window.addEventListener('keypress', function(e) {" +
                        " events.push({type: 'KEYBOARD', key: String.fromCharCode(e.keyCode)});" +
                        " }, true);" +
                        "window._getEvents = function() {" +
                        "  return events;" +
                        " };" +
                        "window._getEvent = function() {" +
                        "  return event;" +
                        " };" +
                        "})(); ");
    }

    public void stop() {
        List<BaseData> steps = new ArrayList<>();
        for (Object baseElement : (ArrayList) ((JavascriptExecutor) driver).executeScript("return window._getEvents();")) {
            String type = (String) ((Map) baseElement).get("type");
            if (Type.valueOf(type) == Type.MOUSE)
                steps.add(new Mouse(getElementXPath(driver, (WebElement) ((Map) baseElement).get("element"))));
            else if (Type.valueOf(type) == Type.KEYBOARD)
                steps.add(new Keyboard((String) ((Map) baseElement).get("key")));
        }
        localStorage.setSteps(steps);
        driver.quit();
    }

    private String getElementXPath(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver).executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();", element);
    }

    public WebElement getWebElement() {
        return Optional.ofNullable(((JavascriptExecutor) driver).executeScript("return window._getEvent();"))
                .map(e->(WebElement) ((Map)e).get("element"))
                .filter(element -> webElement!=element)
                .orElse(null);
    }

}
