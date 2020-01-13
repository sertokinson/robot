package ru.sertok.robot.services;

import com.google.gson.internal.LinkedTreeMap;
import ru.sertok.robot.data.AssertData;

import java.io.File;
import java.util.List;

public interface Database {
    void write(Object data, String folder,String filename);
    List<LinkedTreeMap> read(String name);
    AssertData read(File file);
    void delete(File file);
}
