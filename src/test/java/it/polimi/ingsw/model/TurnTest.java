package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void testGetPlayer() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        assertEquals(player, turn.getPlayer());
    }

    @Test
    void testGetNumber() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        assertEquals(1, turn.getNumber());
    }

    @Test
    void testIsDiePlaced() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        assertFalse(turn.isDiePlaced());
        turn.setDiePlaced();
        assertTrue(turn.isDiePlaced());
    }

    @Test
    void testIsToolCardUsed() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        assertFalse(turn.isToolCardUsed());
        turn.setToolCardUsed();
        assertTrue(turn.isToolCardUsed());
    }

    @Test
    void testSetDiePlaced() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        assertFalse(turn.isDiePlaced());
        turn.setDiePlaced();
        assertTrue(turn.isDiePlaced());
    }

    @Test
    void testSetToolCardUsed() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        assertFalse(turn.isToolCardUsed());
        turn.setToolCardUsed();
        assertTrue(turn.isToolCardUsed());
    }

    @Test
    void testModifyTurn() {
        Player player = new Player("player");
        Turn turn = new Turn(player, 1);
        turn.modifyTurn();
        assertEquals(2, turn.getNumber());
    }
}