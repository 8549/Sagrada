package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWindowTest {

    @Test
    void getCellAt() {
    }

    @Test
    void addDie() {
        Die die1 = new Die(SagradaColor.GREEN.getColor());
        PlayerWindow playerWindow = new PlayerWindow();
        assertTrue(playerWindow.addDie(die1, 1, 2));
        Die die2 = new Die(SagradaColor.BLUE.getColor());
        assertFalse(playerWindow.addDie(die2, 1, 2));
    }

    @Test
    void moveDie() {
        PlayerWindow playerWindow = new PlayerWindow();
        Die die = new Die(SagradaColor.BLUE.getColor());
        playerWindow.addDie(die, 1, 2);
        assertTrue(playerWindow.moveDie(1, 2, 3, 2));
        assertFalse(playerWindow.moveDie(3,1, 2, 3));


    }

    @Test
    void getWindowPattern() {
    }

    @Test
    void checkPlacement() {
    }
}