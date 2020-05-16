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
    private String pathToApp;
    private BrowserName browserName;

    public Status execute(String url) {
        if (pathToApp == null) {
            log.error("Не задан путь до приложения!");
            return Status.ERROR;
        }
        log.debug("Запускаем приложение: {}", pathToApp);
        if (!StringUtils.isEmpty(url)) {
            try {
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", pathToApp, url});
                else
                    new ProcessBuilder(pathToApp, url).start();
                return Status.SUCCESS;
            } catch (IOException e) {
                log.error("Приложение по заданному пути не найдено: {}", pathToApp, e);
                return Status.ERROR;
            }
        } else {
            log.error("Не задан url!");
            return Status.ERROR;
        }
    }

    public void setPathToApp(String pathToApp) {
        log.debug("Установили путь до приложения: {}", pathToApp);
        this.pathToApp = pathToApp;
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

}
