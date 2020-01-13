package ru.sertok.robot;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Component;
import ru.sertok.robot.data.AssertData;
import ru.sertok.robot.services.Database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Component
public class FileBase implements Database {

    public void write(Object data, String folder, String filename) {
        Path path = Paths.get("database");
        if (!Files.exists(path)) {
            new File("database").mkdir();
        }
        new File("database/"+folder).mkdir();
        try (FileWriter writer = new FileWriter("database/"+folder + filename)) {
            writer.write(new Gson().toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LinkedTreeMap> read(String name) {
        try (FileReader reader = new FileReader("database/" + name + "/" + name + ".json")) {
            int c;
            StringBuilder json = new StringBuilder();
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return new Gson().fromJson(String.valueOf(json), List.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    public AssertData read(File file) {
        try (FileReader reader = new FileReader(file)) {
            int c;
            StringBuilder json = new StringBuilder();
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return new Gson().fromJson(String.valueOf(json), AssertData.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void delete(File file) {
        file.delete();
    }
}
