package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.core.hook.EventListener;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Platform;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.data.storage.LocalStorage;
import ru.sertok.robot.entity.BrowserEntity;
import ru.sertok.robot.entity.DesktopEntity;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.response.BaseResponse;
import ru.sertok.robot.response.ResponseBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordControllerImpl implements RecordController {
    private final LocalStorage localStorage;
    private final AppService appService;
    private final EventListener eventListener;
    private final ScreenShotControllerImpl screenShotController;

    @Override
    public BaseResponse start(RecordRequest recordRequest) {
        log.info("REST-запрос ../record/start с параметрами {}", recordRequest);
        localStorage.invalidateLocalStorage();
        TestCase testCase = new TestCase();
        testCase.setPath(recordRequest.getPath());
        testCase.setAppName(recordRequest.getAppName());
        testCase.setDescription(recordRequest.getDescription());
        testCase.setFolderName(recordRequest.getFolderName());
        testCase.setPlatform(Platform.valueOf(recordRequest.getPlatform()));
        testCase.setTestCaseName(recordRequest.getTestCaseName());
        testCase.setUrl(recordRequest.getUrl());
        String appName = recordRequest.getAppName();
        boolean isWeb = Platform.valueOf(recordRequest.getPlatform()) == Platform.WEB;
        if (StringUtils.isEmpty(appName))
            testCase.setAppName(getName(recordRequest.getPath()));
        else {
            if (isWeb) {
                BrowserEntity browser = new RestTemplate().getForObject(
                        "http://192.168.1.67:8080/autotest/settings/browser/{appName}",
                        BrowserEntity.class,
                        appName);
                localStorage.setBrowserId(browser.getId());
                testCase.setPath(browser.getPath());
            } else {
                DesktopEntity desktop = new RestTemplate().getForObject(
                        "http://192.168.1.67:8080/autotest/settings/desktop/{appName}",
                        DesktopEntity.class,
                        appName
                );
                localStorage.setDesktopId(desktop.getId());
                testCase.setPath(desktop.getPath());
            }
        }
        localStorage.setTestCase(testCase);
        String testCaseName = testCase.getTestCaseName();
        if (StringUtils.isEmpty(testCaseName)) {
            String error = "Пустое название тест кейса!";
            log.error(error);
            return ResponseBuilder.error(error);
        }
        if (appService.execute(testCase) == Status.ERROR) {
            String error = "Неверно указан путь до " + (isWeb ? "браузера" : "приложения");
            log.error(error);
            return ResponseBuilder.error(error);
        }
        localStorage.setStartTime(System.currentTimeMillis());
        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                log.error("There was a problem registering the native ru.sertok.hook.", e);
                return ResponseBuilder.error("Проблемы со считывания устройства мыши или клавиатуры");
            }
            GlobalScreen.addNativeMouseListener(eventListener);
            GlobalScreen.addNativeMouseMotionListener(eventListener);
            GlobalScreen.addNativeKeyListener(eventListener);
            GlobalScreen.addNativeMouseWheelListener(eventListener);
        }
        return ResponseBuilder.success();
    }

    private String getName(String path) {
        String regex = "\\\\";
        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            regex = "/";
        String[] split = path.split(regex);
        return split[split.length - 1];
    }

    @Override
    public BaseResponse stop() {
        log.info("REST-запрос ../record/stop");
        appService.stop();
        if (localStorage.isScreenshotStart())
            screenShotController.stop();
        if (!removeHook())
            return ResponseBuilder.error("Проблемы с остановкой слушателя устройства мыши или клавиатуры");
        new RestTemplate().postForLocation(
                "http://192.168.1.67:8080/autotest/record/stop",
                new HttpEntity<>(localStorage));
        localStorage.invalidateLocalStorage();
        return ResponseBuilder.success();
    }

    @Override
    public BaseResponse exit() {
        log.info("REST-запрос ../record/exit");
        localStorage.invalidateLocalStorage();
        return removeHook()
                ? ResponseBuilder.success()
                : ResponseBuilder.error("Проблемы с остановкой слушателя устройства мыши или клавиатуры");
    }

    private boolean removeHook() {
        try {
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.removeNativeKeyListener(eventListener);
            GlobalScreen.removeNativeMouseListener(eventListener);
            GlobalScreen.removeNativeMouseMotionListener(eventListener);
            GlobalScreen.removeNativeMouseWheelListener(eventListener);
            return true;
        } catch (NativeHookException e) {
            log.error("There was a problem unregistering the native ru.sertok.hook.", e);
            return false;
        }
    }
}
