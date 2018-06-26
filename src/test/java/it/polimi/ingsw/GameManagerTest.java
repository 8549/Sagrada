package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void endGame() {
    }


    @Test
    void TestdisconnectPlayer() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        GameManager gameManager = new GameManager(players);
        gameManager.disconnectPlayer(giulia);
        assertEquals(PlayerStatus.DISCONNECTED, giulia.getStatus());
    }

    @Test
    void testreconnectPlayer() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        GameManager gameManager = new GameManager( players);
        gameManager.disconnectPlayer(giulia);
        gameManager.reconnectPlayer(giulia);
        assertEquals(PlayerStatus.ACTIVE, giulia.getStatus());
    }
}