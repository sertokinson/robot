package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.core.ExecuteApp;
import ru.sertok.robot.core.hook.EventListener;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.Browser;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.data.enumerate.Status;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.response.ResponseBuilder;
import ru.sertok.robot.storage.LocalStorage;

import javax.ws.rs.core.Response;
import java.util.List;

import static ru.sertok.robot.utils.Utils.deleteLastMousePressed;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordControllerImpl implements RecordController {
    private final LocalStorage localStorage;
    private final ExecuteApp executeApp;
    private final EventListener eventListener;
    private final Database database;

    @Override
    public Response start(RecordRequest recordRequest) {
        log.debug("REST-запрос ../record/start с параметрами {}", recordRequest);
        if (StringUtils.isEmpty(recordRequest.getTestCaseName())) {
            String error = "Пустое название тест кейса!";
            log.error(error);
            return ResponseBuilder.error(error);
        }
        String url = recordRequest.getUrl();
        localStorage.setTestCase(TestCase.builder()
                .description(recordRequest.getDescription())
                .name(recordRequest.getTestCaseName())
                .url(recordRequest.getUrl())
                .build());
        if (executeApp.execute(url, recordRequest.getPathToApp()) == Status.ERROR) {
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
        try {
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.removeNativeKeyListener(eventListener);
            GlobalScreen.removeNativeMouseListener(eventListener);
            GlobalScreen.removeNativeMouseMotionListener(eventListener);
            GlobalScreen.removeNativeMouseWheelListener(eventListener);
        } catch (NativeHookException e) {
            log.error("There was a problem unregistering the native ru.sertok.hook.", e);
            return ResponseBuilder.error("Проблемы с остановкой слушателя устройства мыши или клавиатуры");
        }
        TestCase testCase = localStorage.getTestCase();
        List<BaseData> steps = localStorage.getSteps();
        database.save(TestCase.builder()
                .image(testCase.getImage())
                .url(testCase.getUrl())
                .name(testCase.getName())
                .steps(steps)
                .description(testCase.getDescription())
                .time((int) (System.currentTimeMillis() - localStorage.getStartTime()))
                .path(executeApp.getPathToApp())
                .os(getOS(userAgent))
                .browser(new Browser(executeApp.getBrowserName().name(), getBrowserVersion(userAgent)))
                .build()
        );
        localStorage.invalidateLocalStorage();
        return ResponseBuilder.ok();
    }


    private String getOS(String userAgent) {
        return userAgent.split("\\(")[0].split("\\)")[0];
    }

    private String getBrowserVersion(String userAgent) {
        String[] strings = userAgent.split("/");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].toUpperCase().contains(executeApp.getBrowserName().toString())) {
                return strings[i + 1].split(" ")[0];
            }
        }
        return null;
    }
}
