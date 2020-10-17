package ru.sertok.robot.core.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.core.storage.LocalStorage;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppService {
    private final LocalStorage localStorage;

    private WebDriver driver;

    public void execute(String url) {
        driver = new ChromeDriver();
        driver.get(url);
        ((JavascriptExecutor) driver)
                .executeScript("(function() { " +
                        "var element = null;" +
                        "window.addEventListener('click', function(e) {" +
                        "element = document.elementFromPoint(e.clientX, e.clientY);" +
                        "}, true);" +
                        "window._getElement = function() {" +
                        "  return element;" +
                        " };" +
                        "})(); ");
    }

    public void stop() {
        driver.quit();
    }
}
