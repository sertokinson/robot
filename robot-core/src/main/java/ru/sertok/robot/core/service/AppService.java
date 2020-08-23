package ru.sertok.robot.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.service.SettingsService;

import static ru.sertok.robot.data.enumerate.Browser.CHROME_PORTABLE;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppService {
    private final SettingsService settingsService;

    public Status execute(TestCase testCase) {
        String pathToApp = testCase.getPath();
        String url = testCase.getUrl();
        if (StringUtils.isEmpty(pathToApp)) {
            pathToApp = settingsService.getPathToApp(testCase);
            if (pathToApp == null) {
                log.error("Не задан путь до придожения!");
                return Status.ERROR;
            }
        }
        log.debug("Запускаем приложение: {}", pathToApp);
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac"))
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", pathToApp, url});
            else {
                if (!StringUtils.isEmpty(url)) {
                    if (pathToApp.equals(CHROME_PORTABLE.getName()))
                        new ProcessBuilder("chrome/Google Chrome Portable.exe", url).start();
                    else new ProcessBuilder(pathToApp, url).start();
                } else {
                    new ProcessBuilder(pathToApp).start();
                }
            }
            return Status.SUCCESS;
        } catch (Exception e) {
            log.error("Приложение по заданному запустить невозможно: {}", pathToApp, e);
            return Status.ERROR;
        }
    }
}
