package ru.sertok.robot.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.sertok.robot.api.ExecuteApp;

import java.io.IOException;

@Component
public class ExecuteAppImpl implements ExecuteApp {
    @Override
    public void execute(String url) {
        if (!StringUtils.isEmpty(url)) {
            try {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", "/Applications/Google Chrome.app", url});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
