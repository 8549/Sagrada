package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.network.server.ClientWrapper;
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
    void removeTurn() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        DiceBag diceBag = DiceBag.getInstance();
        Round round = new Round(players, 1);
        round.removeTurn(giulia, 2);
        assertEquals(5, round.getTurns().size());
        round.removeTurn(andrea, 1);
        assertEquals(3, round.getTurns().size());
    }

}