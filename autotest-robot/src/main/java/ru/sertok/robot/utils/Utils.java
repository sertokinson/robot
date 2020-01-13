package ru.sertok.robot.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<File> filterFiles(String testCase){
        return Arrays.stream(new File("database/" + testCase).listFiles()).filter(file->!file.getName().equals(testCase + ".json")).collect(Collectors.toList());
    }
}
