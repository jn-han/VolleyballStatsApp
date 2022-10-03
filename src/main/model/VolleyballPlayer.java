package model;

import org.json.JSONObject;
import persistence.Writable;


public class VolleyballPlayer implements Writable {
    Statistics playerStats;
    int playerNumber;
    String playerName;

    public VolleyballPlayer(int playerNumber, String playerName) {
        playerStats = new Statistics();
        this.playerNumber = playerNumber;
        this.playerName = playerName;
    }

    public VolleyballPlayer(int playerNumber, String playerName, Statistics playerStats) {
        this.playerNumber = playerNumber;
        this.playerName = playerName;
        this.playerStats = playerStats;
    }

    public Statistics getPlayerStats() {
        return playerStats;
    }

    public void addDig() {
        playerStats.addDig();
        EventLog.getInstance().logEvent(new Event("Dig has been added to: #" + playerNumber + " " + playerName + "\n"));
    }

    public void addKill() {
        playerStats.addKill();
        EventLog.getInstance().logEvent(new Event("Kill has been added to: #" + playerNumber + " " + playerName
                + "\n"));
    }

    public void addBlock() {
        playerStats.addBlock();
        EventLog.getInstance().logEvent(new Event("Block has been added to: #" + playerNumber + " " + playerName
                + "\n"));
    }

    public void addAssist() {
        playerStats.addAssist();
        EventLog.getInstance().logEvent(new Event("Assist has been added to: #" + playerNumber + " " + playerName
                + "\n"));
    }


    public void addAce() {
        playerStats.addAce();
        EventLog.getInstance().logEvent(new Event("Ace has been added to: #" + playerNumber + " "
                + playerName + "\n"));
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
        EventLog.getInstance().logEvent(new Event(playerName + "'s number has been switched to " + this.playerNumber
                + "\n"));
    }

    public void setPlayerName(String playerName) {
        String oldName = this.playerName;
        this.playerName = playerName;
        EventLog.getInstance().logEvent(new Event(oldName + "'s name was switched to " + this.playerName + "\n"));

    }

    public String getPlayerName() {
        return playerName;
    }
    

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerName", playerName);
        json.put("playerNumber", playerNumber);
        json.put("playerStats", playerStats.toJson());
        return json;
    }
}