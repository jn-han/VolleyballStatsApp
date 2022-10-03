package persistence;

import model.Statistics;
import model.Team;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.VolleyballPlayer;
import org.json.*;
//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private java.lang.String source;

    public JsonReader(java.lang.String source) {
        this.source = source;
    }

    public Team read() throws IOException {
        java.lang.String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeam(jsonObject);
    }


    private java.lang.String readFile(java.lang.String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<java.lang.String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    private Team parseTeam(JSONObject jsonObject) {
        java.lang.String name = jsonObject.getString("name");
        Team team = new Team(name);
        addTeam(team, jsonObject);
        return team;
    }

    private void addTeam(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addAttributes(team, nextPlayer);
        }
    }


    private void addAttributes(Team team, JSONObject jsonObject) {
        int number = jsonObject.getInt("playerNumber");
        String name = jsonObject.getString("playerName");
        JSONObject ps = (JSONObject) jsonObject.get("playerStats");
        int kills = ps.getInt("kills");
        int digs = ps.getInt("digs");
        int assists = ps.getInt("assists");
        int blocks = ps.getInt("blocks");
        int aces = ps.getInt("aces");
        Statistics saveStats = new Statistics(digs, kills, assists, blocks, aces);
        VolleyballPlayer player = new VolleyballPlayer(number, name, saveStats);
        team.addPlayer(player);
    }
}
