package model;

import org.json.JSONObject;
import persistence.Writable;


public class Statistics implements Writable {
    private int digs;
    private int kills;
    private int assists;
    private int blocks;
    private int aces;

    public Statistics() {
        this.digs = 0;
        this.kills = 0;
        this.assists = 0;
        this.blocks = 0;
        this.aces = 0;
    }

    public Statistics(int digs, int kills, int assists, int blocks, int aces) {
        this.digs = digs;
        this.kills = kills;
        this.assists = assists;
        this.blocks = blocks;
        this.aces = aces;
    }

    public void addDig() {
        digs++;
    }

    public void addAssist() {
        assists++;
    }

    public void addKill() {
        kills++;
    }

    public void addBlock() {
        blocks++;
    }

    public void addAce() {
        aces++;
    }


    public int getKills() {
        return kills;
    }


    public int getAssists() {
        return assists;
    }


    public int getBlocks() {
        return blocks;
    }


    public int getAces() {
        return aces;
    }

    public int getDigs() {
        return digs;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("digs", digs);
        json.put("kills", kills);
        json.put("assists", assists);
        json.put("blocks", blocks);
        json.put("aces", aces);
        return json;
    }

}
