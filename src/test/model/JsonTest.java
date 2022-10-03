package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
//Inspired by JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkTeam(String name, int number, VolleyballPlayer player) {
        assertEquals(number, player.getPlayerNumber());
        assertEquals(name, player.getPlayerName());
    }
}
