package ru.sertok.robot.core.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sertok.robot.core.storage.LocalStorage;
import ru.sertok.robot.data.Driver;
import ru.sertok.robot.data.TestCase;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppService {
    private final LocalStorage localStorage;

    private WebDriver driver;

    public void execute(TestCase testCase) {
        Driver driver = Driver.valueOf(testCase.getBrowserName().toUpperCase());
        String path = "driver/" + driver.getName() + "driver";
        if (System.getProperty("os.name").toLowerCase().contains("windows"))
            path += ".exe";
        System.setProperty("webdriver." + driver.getName() + ".driver", path);
        switch (driver) {
            case CHROME:
                this.driver = new ChromeDriver();
                break;
            case FIREFOX:
                this.driver = new FirefoxDriver();
        }
        this.driver.get(testCase.getUrl());
        ((JavascriptExecutor) this.driver)
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
