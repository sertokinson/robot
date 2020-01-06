package ru.sertok.services;

import com.google.gson.internal.LinkedTreeMap;
import ru.sertok.data.AssertData;

import java.io.File;
import java.util.List;

public interface Database {
    void write(Object data, String folder,String filename);
    List<LinkedTreeMap> read(String path);
    AssertData read(File file);
}
