package ru.sertok.robot.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.sertok.robot.data.enumerate.BrowserName;
import ru.sertok.robot.data.enumerate.Status;

import java.io.IOException;

@Slf4j
@Getter
@Component
public class ExecuteApp {
    private BrowserName browserName;
    private String pathToApp;

    public Status execute(String url, String pathToApp) {
        if (StringUtils.isEmpty(pathToApp)) {
            log.error("Не задан путь до приложения!");
            return Status.ERROR;
        }
        this.pathToApp = pathToApp;
        log.debug("Запускаем приложение: {}", pathToApp);
        if (!StringUtils.isEmpty(url)) {
            if (pathToApp.toUpperCase().contains(BrowserName.CHROME.toString())) {
                browserName = BrowserName.CHROME;
            } else if (pathToApp.toUpperCase().contains(BrowserName.FIREFOX.toString())) {
                browserName = BrowserName.FIREFOX;
            } else if (pathToApp.toUpperCase().contains(BrowserName.SAFARI.toString())) {
                browserName = BrowserName.SAFARI;
            } else {
                browserName = BrowserName.UNKNOWN;
            }
            log.debug("Установили браузер: {}", browserName);
        }
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac"))
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", pathToApp, url});
            else {
                if (!StringUtils.isEmpty(url))
                    new ProcessBuilder(pathToApp, url).start();
                else new ProcessBuilder(pathToApp).start();
            }
            return Status.SUCCESS;
        } catch (Exception e) {
            log.error("Приложение по заданному запустить невозможно: {}", pathToApp, e);
            return Status.ERROR;
        }
    }
}
