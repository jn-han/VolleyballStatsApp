package persistence;


import model.JsonTest;
import model.Team;
import model.VolleyballPlayer;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Team team = new Team("My team");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriterEmptyTeam() {
        try {
            Team team = new Team("My team");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeam.json");
            writer.open();
            writer.write(team);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeam.json");
            team = reader.read();
            assertEquals("My team", team.getTeamName());
            assertEquals(0, team.numPlayers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTeam() {
        try {
            Team team = new Team("My team");
            team.addPlayer(new VolleyballPlayer(14, "Joshua Nguyen"));
            team.addPlayer(new VolleyballPlayer(9, "Brandon Bae"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTeam.json");
            writer.open();
            writer.write(team);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTeam.json");
            team = reader.read();
            assertEquals("My team", team.getTeamName());
            List<VolleyballPlayer> players = team.getTeam();
            assertEquals(2, players.size());
            checkTeam("Joshua Nguyen", 14, players.get(0));
            checkTeam("Brandon Bae", 9, players.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}