package hook;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {
    private String testCase;

    public Database(String testCase) {
        this.testCase = testCase;
    }

    public void write(String data) {
        try (FileWriter writer = new FileWriter("database/" + testCase + ".json")) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LinkedTreeMap> read() {
        try (FileReader reader = new FileReader("database/" + testCase + ".json")) {
            int c;
            StringBuilder json = new StringBuilder();
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return new Gson().fromJson(String.valueOf(json), ArrayList.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }
}
