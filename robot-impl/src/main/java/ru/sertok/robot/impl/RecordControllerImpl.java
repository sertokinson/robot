package ru.sertok.robot.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sertok.robot.api.RecordController;
import ru.sertok.robot.core.ExecuteApp;
import ru.sertok.robot.core.hook.EventListener;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.database.Database;
import ru.sertok.robot.request.RecordRequest;
import ru.sertok.robot.storage.LocalStorage;

import javax.ws.rs.core.Response;

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
        String url = recordRequest.getUrl();
        localStorage.setStartTime(System.currentTimeMillis());
        localStorage.setTestCase(TestCase.builder()
                .name(recordRequest.getTestCaseName())
                .url(recordRequest.getUrl())
                .build());
        if (!executeApp.execute(url)) {
            log.error("Не удалось запустить приложение!");
            return Response.serverError().build();
        }
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            log.error("There was a problem registering the native ru.sertok.hook.", e);
            return Response.serverError().build();
        }
        GlobalScreen.addNativeMouseListener(eventListener);
        GlobalScreen.addNativeMouseMotionListener(eventListener);
        GlobalScreen.addNativeKeyListener(eventListener);
        return Response.ok().build();
    }

    @Override
    public Response stop() {
        log.debug("REST-запрос ../record/stop");
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            log.error("There was a problem unregistering the native ru.sertok.hook.", e);
            return Response.serverError().build();
        }
        TestCase testCase = localStorage.getTestCase();
        testCase.setSteps(localStorage.getSteps());
        database.save(testCase);
        return Response.ok().build();
    }
}
