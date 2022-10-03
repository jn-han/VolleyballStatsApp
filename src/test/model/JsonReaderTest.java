package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Team team = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyTeam() {
        JsonReader reader = new JsonReader("./data/emptyTeam.json");
        try {
            Team team = reader.read();
            assertEquals("Your Team", team.getTeamName());
            assertEquals(0, team.numPlayers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTeam() {
        JsonReader reader = new JsonReader("./data/team.json");
        try {
            Team team = reader.read();
            assertEquals("Your Team", team.getTeamName());
            List<VolleyballPlayer> players = team.getTeam();
            assertEquals(1, players.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
