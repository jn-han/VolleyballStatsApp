package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team implements Writable {
    private String teamName;
    private ArrayList<VolleyballPlayer> team;

    // EFFECTS: constructs team with a name and empty list of Volleyball Players
    public Team(String name) {
        this.teamName = name;
        EventLog.getInstance().logEvent(new Event("Team name has been set to: " + teamName + "\n"));
        team = new ArrayList<>();
    }

    public Team() {
        this.teamName = "Basic Team";
        team = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
        EventLog.getInstance().logEvent(new Event("Team Name has been set to: " + this.teamName + "\n"));
    }

    // MODIFIES: this
    // EFFECTS: adds Volleyball Player to this team
    public void addPlayer(VolleyballPlayer player) {
        team.add(player);
        EventLog.getInstance().logEvent(new Event(player.getPlayerName() + " has been added to " + teamName + "\n"));
    }

    // EFFECTS: returns an unmodifiable list of Volleyball Players in this team
    public List<VolleyballPlayer> getTeam() {
        return team;
    }

    // EFFECTS: returns number of Volleyball Players in this team
    public int numPlayers() {
        return team.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", teamName);
        json.put("team", teamToJson());
        return json;
    }

    // EFFECTS: returns Volleyball Players in this team as a JSON array
    private JSONArray teamToJson() {
        JSONArray jsonArray = new JSONArray();

        for (VolleyballPlayer t : team) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
