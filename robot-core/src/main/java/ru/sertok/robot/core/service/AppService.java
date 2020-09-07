package ru.sertok.robot.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Status;

import java.io.IOException;

@Slf4j
@Service
public class AppService {

    public Status execute(TestCase testCase) {
        String pathToApp = testCase.getPath();
        String url = testCase.getUrl();
        if (StringUtils.isEmpty(pathToApp)) {
            pathToApp = new RestTemplate().postForObject(
                    testCase.getHost()+"/autotest/settings/pathToApp",
                    new HttpEntity<>(testCase), String.class);
        }
        log.debug("Запускаем приложение: {}", pathToApp);
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac"))
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", pathToApp, url});
            else {
                if (!StringUtils.isEmpty(url))
                    new ProcessBuilder(pathToApp, url, "--kiosk").start();
                else
                    new ProcessBuilder(pathToApp).start();
            }
            return Status.SUCCESS;
        } catch (Exception e) {
            log.error("Приложение по заданному запустить невозможно: {}", pathToApp, e);
            return Status.ERROR;
        }
    }

    public void stop(){
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        } catch (IOException e) {
            log.error("Не удалось убить процесс");
        }
    }
}
