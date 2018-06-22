package it.polimi.ingsw.model;

import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.network.server.ServerInterface;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void setTurns() {
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
        Round round = new Round(players, 1);
        assertEquals(round.getTurns().get(0).getPlayer(), marco);
        assertEquals(round.getTurns().get(7).getPlayer(), marco);
        assertEquals(round.getTurns().get(1).getPlayer(), giulia);
        assertEquals(round.getTurns().get(6).getPlayer(), giulia);
        assertEquals(round.getTurns().get(2).getPlayer(), andrea);
        assertEquals(round.getTurns().get(5).getPlayer(), andrea);
        assertEquals(round.getTurns().get(3).getPlayer(), francesca);
        assertEquals(round.getTurns().get(4).getPlayer(), francesca);
    }


    @Test
    void doubledTurn() {
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
        Round round = new Round(players, 1);
        round.doubledTurn(2);
        assertEquals(andrea, round.getTurns().get(3).getPlayer());
    }
}