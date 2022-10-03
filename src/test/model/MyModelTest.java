package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyModelTest {
    VolleyballPlayer player1;
    Statistics stats1;
    Team team;


    @BeforeEach
    void runBefore(){
        player1 = new VolleyballPlayer(14, "Joshua Nguyen");
        team = new Team("My team");
        stats1 = new Statistics();
        stats1.addAssist();
        stats1.addBlock();
        stats1.addKill();
        stats1.addAce();
    }


    @Test
    void testGetAce(){
        assertEquals(1, stats1.getAces());
    }

    @Test
    void testAddBlock(){
        assertEquals(1, stats1.getBlocks());

    }
    @Test
    void testAddAssist(){
        assertEquals(1, stats1.getAssists());
    }



    @Test
    void testGetPlayerStats(){
        assertEquals(0, player1.getPlayerStats().getAces());
        player1.getPlayerStats().addKill();
        assertEquals(1, player1.getPlayerStats().getKills());

    }

    @Test
    void testGetPlayerNumber(){
        assertEquals(14, player1.getPlayerNumber());

    }

    @Test
    void testGetPlayerName(){
        assertEquals("Joshua Nguyen", player1.getPlayerName());
    }

    @Test
    void testGetPlayerAce(){
        assertEquals(0, player1.getPlayerStats().getAces());
    }

    @Test
    void testGetPlayerKill() {
        player1.getPlayerStats().addKill();
        assertEquals(1, player1.getPlayerStats().getKills());
    }

    @Test
    void testGetPlayerAssist() {
        assertEquals(0, player1.getPlayerStats().getAssists());
        player1.getPlayerStats().addAssist();
        assertEquals(1, player1.getPlayerStats().getAssists());
    }

    @Test
    void testGetPlayerBlocks() {
        assertEquals(0, player1.getPlayerStats().getBlocks());
        player1.getPlayerStats().addBlock();
        assertEquals(1, player1.getPlayerStats().getBlocks());
    }

    @Test
    void testGetDigs() {
        player1.getPlayerStats().addDig();
        assertEquals(1, player1.getPlayerStats().getDigs());
        player1.getPlayerStats().addDig();
    }

    @Test
    void testSetPlayerNumber() {
        assertEquals(14, player1.getPlayerNumber());
        player1.setPlayerNumber(7);
        assertEquals(7, player1.getPlayerNumber());
    }

    @Test
    void testSetPlayerName() {
        assertEquals("Joshua Nguyen", player1.getPlayerName());
        player1.setPlayerName("Jaiden");
        assertEquals("Jaiden", player1.getPlayerName());
    }

    @Test
    void testSetTeamName() {
        assertEquals("My Team", team.getTeamName());
        team.setTeamName("Your Team");
        assertEquals("Your Team", team.getTeamName());
    }


}