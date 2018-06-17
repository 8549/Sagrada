package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.network.server.SocketServer;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void endGame() {
    }


    @Test
    void disconnectPlayer() {
        String[] args = new String[3];
        MainServer mainServer = new MainServer(args);
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = DiceBag.getInstance();
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        roundPlayers.add(francesca);
        Round round = new Round(roundPlayers, 1);
        GameManager gameManager = new GameManager(mainServer, players);

        gameManager.disconnectPlayer(giulia, round);
        assertEquals(6, round.getTurns().size());
    }

    @Test
    void reconnectPlayer() {
        String[] args = new String[3];
        MainServer mainServer = new MainServer(args);
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = DiceBag.getInstance();
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        roundPlayers.add(francesca);
        Round round = new Round(roundPlayers, 1);
        GameManager gameManager = new GameManager(mainServer, players);

        gameManager.disconnectPlayer(giulia, round);
        assertEquals(6, round.getTurns().size());


        gameManager.reconnectPlayer(francesca, round);
        assertEquals(8, round.getTurns().size());
    }

    @Test
    void gameLoop() {
        String[] args = new String[3];
        MainServer mainServer = new MainServer(args);
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = DiceBag.getInstance();
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        roundPlayers.add(francesca);
        Round round = new Round(roundPlayers, 1);
        GameManager gameManager = new GameManager(mainServer, players);
        gameManager.startRound();

    }
}