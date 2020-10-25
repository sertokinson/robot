package ru.sertok.robot.core.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    @SneakyThrows
    public void execute(TestCase testCase) {
        Driver driver = Driver.valueOf(testCase.getBrowserName().toUpperCase());
        String path = "driver/" + driver.getName() + "driver";
        if (System.getProperty("os.name").toLowerCase().contains("windows"))
            path += ".exe";
        System.setProperty("webdriver." + driver.getName() + ".driver", path);
        switch (driver) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                chromeOptions.addArguments("--kiosk");
                this.driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                this.driver = new FirefoxDriver();
        }
        this.driver.get(testCase.getUrl());
    }


    public void stop() {
        driver.quit();
    }
}
