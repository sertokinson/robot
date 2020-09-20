package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.core.hook.EventListener;
import ru.sertok.robot.core.service.AppService;
import ru.sertok.robot.core.storage.LocalStorage;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.mapper.TestCaseMapper;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.response.BaseResponse;
import ru.sertok.robot.response.ResponseBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordControllerImpl implements RecordController {
    private final AppService appService;
    private final TestCaseMapper testCaseMapper;
    private final LocalStorage localStorage;
    private final EventListener eventListener;

    @Override
    public BaseResponse start(RecordRequest recordRequest) {
        log.info("REST-запрос ../record/start с параметрами {}", recordRequest);
        localStorage.invalidateLocalStorage();
        TestCase testCase = testCaseMapper.toTestCase(recordRequest);
        localStorage.setStartTime(System.currentTimeMillis());
        appService.execute(testCase.getUrl());
        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                log.error("There was a problem registering the native ru.sertok.hook.", e);
            }
            GlobalScreen.addNativeMouseListener(eventListener);
        }
        localStorage.setTestCase(testCase);
        return ResponseBuilder.success();
    }

    @Override
    public BaseResponse stop() {
        log.info("REST-запрос ../record/stop");
        removeHook();
        appService.stop();
        new RestTemplate().postForLocation(
                localStorage.getTestCase().getHost()+"/autotest/record/stop",
                new HttpEntity<>(localStorage));
        localStorage.invalidateLocalStorage();
        return ResponseBuilder.success();
    }

    @Override
    public BaseResponse exit() {
        log.info("REST-запрос ../record/exit");
        localStorage.invalidateLocalStorage();
        return ResponseBuilder.success();
    }

    private void removeHook() {
        try {
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.removeNativeMouseListener(eventListener);
        } catch (NativeHookException e) {
            log.error("There was a problem unregistering the native ru.sertok.hook.", e);
        }
    }
}
