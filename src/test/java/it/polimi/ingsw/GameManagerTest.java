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
   /*     MainServer mainServer = new MainServer();
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
        GameManager gameManager = new GameManager();

        gameManager.disconnectPlayer(giulia, round);
        assertEquals(6, round.getTurns().size());*/
    }

    @Test
    void reconnectPlayer() {
        /*

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
        GameManager gameManager = new GameManager(serverInterface, players);

        gameManager.disconnectPlayer(giulia, round);
        assertEquals(6, round.getTurns().size());


        gameManager.reconnectPlayer(francesca, round);
        assertEquals(8, round.getTurns().size());*/
    }

    @Test
    void gameLoop() {

        /*
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
        GameManager gameManager = new GameManager(serverInterface, players);
        gameManager.gameLoop();*/

    }

    @Test
    void checkConstraints() {
        /*
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        GameManager gameManager = new GameManager(serverInterface, players);
        List<PatternCard> cards = new ArrayList<>();
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];

        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);

        Die die = new Die(SagradaColor.PURPLE);
        die.setNumber(3);

        assertFalse(gameManager.checkConstraints(virtus, 2, 2, die));
        assertTrue(gameManager.checkConstraints(virtus, 2, 1, die));*/
    }

}