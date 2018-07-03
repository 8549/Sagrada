package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PlayerWindow;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWindowTest {

    @Test
    void testGetCellAt() {
        PlayerWindow playerWindow = new PlayerWindow();
        Die die = new Die(SagradaColor.RED);
        playerWindow.addDie(die, 3, 4);
        assertEquals(die, playerWindow.getCellAt(3,4).getDie());
    }

    @Test
    void testAddDie() {
        Die die1 = new Die(SagradaColor.GREEN);
        PlayerWindow playerWindow = new PlayerWindow();
        assertTrue(playerWindow.addDie(die1, 1, 2));
        Die die2 = new Die(SagradaColor.BLUE);
        assertFalse(playerWindow.addDie(die2, 1, 2));
    }

    @Test
    void testMoveDie() {
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
    void testGetWindowPattern() {
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
        PlayerWindow playerWindow = new PlayerWindow();
        playerWindow.setWindowPattern(virtus);
        assertEquals(virtus, playerWindow.getWindowPattern());


    }
    @Test
    void testIsFirstPlacement() {
        PlayerWindow playerWindow = new PlayerWindow();
        assertTrue(playerWindow.isFirstPlacement());
    }

    @Test
    void testSetWindowPattern() {
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
        PlayerWindow playerWindow = new PlayerWindow();
        playerWindow.setWindowPattern(virtus);
        assertEquals(virtus, playerWindow.getWindowPattern());
    }

    @Test
    void testDieCount() {
        Player marco = new Player("marco");
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
        Die[] dice = new Die[7];
        dice[0] = new Die(SagradaColor.RED);
        dice[0].setNumber(5);
        marco.getPlayerWindow().getDiceGrid()[0][3].setDie(dice[0]);
        dice[1] = new Die(SagradaColor.YELLOW);
        dice[1].setNumber(6);
        marco.getPlayerWindow().getDiceGrid()[1][2].setDie(dice[1]);
        dice[2] = new Die(SagradaColor.GREEN);
        dice[2].setNumber(3);
        marco.getPlayerWindow().getDiceGrid()[1][3].setDie(dice[2]);
        dice[3] = new Die(SagradaColor.PURPLE);
        dice[3].setNumber(2);
        marco.getPlayerWindow().getDiceGrid()[1][4].setDie(dice[3]);
        dice[4] = new Die(SagradaColor.GREEN);
        dice[4].setNumber(2);
        marco.getPlayerWindow().getDiceGrid()[2][2].setDie(dice[4]);
        dice[5] = new Die(SagradaColor.RED);
        dice[5].setNumber(4);
        marco.getPlayerWindow().getDiceGrid()[2][3].setDie(dice[5]);
        dice[6] = new Die(SagradaColor.BLUE);
        dice[6].setNumber(1);
        marco.getPlayerWindow().getDiceGrid()[2][4].setDie(dice[6]);
        assertEquals(7, marco.getPlayerWindow().dieCount());

    }

    @Test
    void getDiceGrid() {
        PlayerWindow playerWindow = new PlayerWindow();
        assertTrue(playerWindow.getDiceGrid()!=null);
    }
}