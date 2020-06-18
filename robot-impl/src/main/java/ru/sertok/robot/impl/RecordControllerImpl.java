package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.core.hook.EventListener;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.mapper.TestCaseMapper;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.response.AppResponse;
import ru.sertok.robot.response.BaseResponse;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.service.SettingsService;
import ru.sertok.robot.storage.LocalStorage;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordControllerImpl implements RecordController {
    private final LocalStorage localStorage;
    private final AppService appService;
    private final EventListener eventListener;
    private final Database database;
    private final TestCaseMapper testCaseMapper;
    private final SettingsService settingsService;

    @Override
    public BaseResponse start(RecordRequest recordRequest) {
        log.debug("REST-запрос ../record/start с параметрами {}", recordRequest);
        localStorage.invalidateLocalStorage();
        TestCase testCase = testCaseMapper.toTestCase(recordRequest);
        localStorage.setTestCase(testCase);
        localStorage.setNewApp(recordRequest.getApp().getIsNew());
        localStorage.setNewUrl(recordRequest.getUrl().getIsNew());
        return record(testCase);
    }

    @Override
    public BaseResponse continued() {
        log.debug("REST-запрос ../record/continued");
        return record(localStorage.getTestCase());
    }

    private BaseResponse record(TestCase testCase) {
        String testCaseName = testCase.getTestCaseName();
        if (StringUtils.isEmpty(testCaseName)) {
            String error = "Пустое название тест кейса!";
            log.error(error);
            return ResponseBuilder.error(error);
        }
        if (!localStorage.isWarningTestCaseName() && database.get(testCaseName) != null) {
            String warning = "Тест с таким именем уже существует, перезаписать?";
            log.warn(warning);
            localStorage.setWarningTestCaseName(true);
            return ResponseBuilder.warning(AppResponse.builder().result(warning).build());
        }
        String appName = testCase.getAppName();
        Boolean isBrowser = testCase.getIsBrowser();
        if (localStorage.isNewApp()) {
            if (!localStorage.isWarningBrowser() && isBrowser && settingsService.getBrowser(appName) != null) {
                String warning = "Браузер с таким именем уже существует, перезаписать?";
                log.warn(warning);
                localStorage.setWarningBrowser(true);
                return ResponseBuilder.warning(AppResponse.builder().result(warning).build());
            }
            if (!localStorage.isWarningDesktop() && !isBrowser && settingsService.getDesktop(appName) != null) {
                String warning = "Приложение с таким именем уже существует, перезаписать?";
                log.warn(warning);
                localStorage.setWarningDesktop(true);
                return ResponseBuilder.warning(AppResponse.builder().result(warning).build());
            }
        }
        if (localStorage.isNewUrl() && isBrowser && settingsService.getUrl(testCase.getUrl()) != null) {
            String error = "Такой url уже существует!";
            log.error(error);
            return ResponseBuilder.error(error);
        }
        if (appService.execute(testCase) == Status.ERROR) {
            String error = "Не удалось запустить приложение!";
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

    @Override
    public BaseResponse stop(String userAgent) {
        log.debug("REST-запрос ../record/stop");
        if (!removeHook())
            return ResponseBuilder.error("Проблемы с остановкой слушателя устройства мыши или клавиатуры");
        database.save();
        localStorage.invalidateLocalStorage();
        return ResponseBuilder.success();
    }

    @Override
    public BaseResponse exit() {
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
