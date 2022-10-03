package persistence;

import java.io.*;

import model.Team;
import org.json.JSONObject;
//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(Team team) {
        JSONObject json = team.toJson();
        saveToFile(json.toString(TAB));
    }

    public void saveToFile(String json) {
        writer.print(json);
    }

    public void close() {
        writer.close();
    }
}
