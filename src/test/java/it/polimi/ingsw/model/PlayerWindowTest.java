package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PlayerWindow;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWindowTest {

    @Test
    void getCellAt() {
    }

    @Test
    void addDie() {
        Die die1 = new Die(SagradaColor.GREEN);
        PlayerWindow playerWindow = new PlayerWindow();
        assertTrue(playerWindow.addDie(die1, 1, 2));
        Die die2 = new Die(SagradaColor.BLUE);
        assertFalse(playerWindow.addDie(die2, 1, 2));
    }

    @Test
    void moveDie() {
        PlayerWindow playerWindow = new PlayerWindow();
        Die die = new Die(SagradaColor.BLUE);
        assertEquals(playerWindow.dieCount(), 0);
        assertTrue(playerWindow.getCellAt(1, 2).isEmpty());
        assertTrue(playerWindow.addDie(die, 1, 2));
        assertEquals(playerWindow.dieCount(), 1);
        assertEquals(die, playerWindow.getCellAt(1, 2).getDie());

        assertTrue(playerWindow.moveDie(1, 2, 3, 3));
        assertEquals(playerWindow.dieCount(), 1);
        assertTrue(playerWindow.getCellAt(1, 2).isEmpty());
        assertEquals(die, playerWindow.getCellAt(3, 3).getDie());

        assertTrue(playerWindow.moveDie(3, 3, 0, 0));
        assertEquals(playerWindow.dieCount(), 1);
        assertTrue(playerWindow.getCellAt(3, 3).isEmpty());
        assertEquals(die, playerWindow.getCellAt(0, 0).getDie());

        assertTrue(playerWindow.addDie(new Die(SagradaColor.RED), 3, 4));
        assertFalse(playerWindow.moveDie(0, 0, 3, 4));
        assertFalse(playerWindow.addDie(new Die(SagradaColor.GREEN), 3, 4));
    }

    @Test
    void getWindowPattern() {
    }

    @Test
    void checkPlacement() {
    }
}