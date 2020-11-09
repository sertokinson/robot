package ru.sertok.robot.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.BaseData;
import ru.sertok.robot.data.TestCase;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class LocalStorage {
    private String host;
    private List<BaseData> steps;
    private TestCase testCase;

    public void invalidateLocalStorage() {
        host = null;
        testCase = null;
        steps = new ArrayList<>();
    }
}
