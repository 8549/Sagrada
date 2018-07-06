package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void testSetTurns() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = new DiceBag();
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
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
    void testDoubledTurn() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        round.doubledTurn(2);
        assertEquals(andrea, round.getTurns().get(3).getPlayer());
    }

    @Test
    void testGetTurn() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        round.setTurns();
        assertEquals(marco, round.getTurn().getPlayer());
    }

    @Test
    void testPassCurrentTurn() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        round.passCurrentTurn();
        assertEquals(1, round.getCurrentTurn());

    }

    @Test
    void testGetPlayers() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        assertEquals(players, round.getPlayers());
    }

    @Test
    void testGetTurns() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        round.setTurns();
        assertEquals(4, round.getTurns().size());
    }

    @Test
    void testGetCurrentTurn() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        round.passCurrentTurn();
        assertEquals(1, round.getCurrentTurn());
    }

    @Test
    void testGetDraftPool() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        assertEquals(5, round.getDraftPool().size());
    }

    @Test
    void testRemoveDieFromDraftPool() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Board board = new Board();
        board.setDiceBag();
        Round round = new Round(players, 1, board);
        round.removeDieFromDraftPool(round.getDraftPool().get(2));
        assertEquals(4, round.getDraftPool().size());
    }
}