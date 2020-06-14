package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.core.hook.EventListener;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.mapper.TestCaseMapper;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.storage.LocalStorage;

import javax.ws.rs.core.Response;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordControllerImpl implements RecordController {
    private final LocalStorage localStorage;
    private final AppService appService;
    private final EventListener eventListener;
    private final Database database;
    private final TestCaseMapper testCaseMapper;

    @Override
    public Response start(RecordRequest recordRequest) {
        log.debug("REST-запрос ../record/start с параметрами {}", recordRequest);
        localStorage.invalidateLocalStorage();
        if (StringUtils.isEmpty(recordRequest.getTestCaseName())) {
            String error = "Пустое название тест кейса!";
            log.error(error);
            return ResponseBuilder.error(error);
        }
        TestCase testCase = testCaseMapper.toTestCase(recordRequest);
        localStorage.setTestCase(testCase);
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
        return ResponseBuilder.ok();
    }

    @Override
    public Response stop(String userAgent) {
        log.debug("REST-запрос ../record/stop");
        if (!removeHook())
            return ResponseBuilder.error("Проблемы с остановкой слушателя устройства мыши или клавиатуры");
        database.save();
        localStorage.invalidateLocalStorage();
        return ResponseBuilder.ok();
    }

    @Override
    public Response exit() {
        localStorage.invalidateLocalStorage();
        return removeHook()
                ? ResponseBuilder.ok()
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
