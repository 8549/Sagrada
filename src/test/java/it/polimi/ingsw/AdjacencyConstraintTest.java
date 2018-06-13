package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyConstraintTest {

    @Test
    void checkAdjacencyFirstDie() {
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            assertTrue(AdjacencyConstraint.checkAdjacencyFirstDie(i, 0));
            assertTrue(AdjacencyConstraint.checkAdjacencyFirstDie(i, 4));
        }
        for (int i = 0; i < WindowPattern.COLUMNS; i++) {
            assertTrue(AdjacencyConstraint.checkAdjacencyFirstDie(0, i));
            assertTrue(AdjacencyConstraint.checkAdjacencyFirstDie(3, i));
        }
        assertFalse(AdjacencyConstraint.checkAdjacencyFirstDie(2, 3));
    }

    @Test
    void checkAdjacency() {
        Cell[][] grid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
        int w = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                grid[i][j] = new Cell();
                w++;
            }
        }
        Die[] dice = new Die[8];
        dice[0] = new Die(SagradaColor.PURPLE);
        dice[0].setNumber(1);
        grid[0][3].setDie(dice[0]);
        dice[1] = new Die(SagradaColor.BLUE);
        dice[1].setNumber(2);
        grid[1][2].setDie(dice[1]);
        dice[2] = new Die(SagradaColor.YELLOW);
        dice[2].setNumber(4);
        grid[1][3].setDie(dice[2]);
        dice[3] = new Die(SagradaColor.GREEN);
        dice[3].setNumber(3);
        grid[1][4].setDie(dice[3]);
        dice[4] = new Die(SagradaColor.YELLOW);
        dice[4].setNumber(6);
        grid[2][2].setDie(dice[4]);
        dice[5] = new Die(SagradaColor.RED);
        dice[5].setNumber(6);
        grid[2][3].setDie(dice[5]);
        dice[6] = new Die(SagradaColor.RED);
        dice[6].setNumber(5);
        grid[2][4].setDie(dice[6]);
        dice[7] = new Die(SagradaColor.PURPLE);
        dice[7].setNumber(6);
        grid[3][4].setDie(dice[7]);
        assertFalse(AdjacencyConstraint.checkAdjacency(grid, 2, 4, dice[6]));
        assertFalse(AdjacencyConstraint.checkAdjacency(grid, 2, 2,dice[4]));
        assertTrue(AdjacencyConstraint.checkAdjacency(grid, 1, 3, dice[2]));

    }

    @Test
    void checkCell() {
        Cell[][] grid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
        Die dieForGrid = new Die(SagradaColor.RED);
        dieForGrid.setNumber(5);
        grid[3][4] = new Cell();
        grid[3][4].setDie(dieForGrid);
        Die die = new Die(SagradaColor.GREEN);
        die.setNumber(3);
        Die die1 = new Die(SagradaColor.RED);
        die1.setNumber(3);
        Die die2 = new Die(SagradaColor.GREEN);
        die2.setNumber(5);
        Die die3 = new Die(SagradaColor.RED);
        assertFalse(AdjacencyConstraint.controlCell(grid, 3, 4, die1)); // same color
        assertFalse(AdjacencyConstraint.controlCell(grid, 3, 4, die2)); // same number
        assertFalse(AdjacencyConstraint.controlCell(grid, 3, 4, dieForGrid)); // same number and color
        assertTrue(AdjacencyConstraint.controlCell(grid, 3, 4, die)); // different number and color

    }

    @Test
    void checkCellAdjacency() {
        Cell[][] grid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
        int w = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                grid[i][j] = new Cell();
                w++;
            }
        }
        assertFalse(AdjacencyConstraint.checkCellAdjacency(grid, 3, 4));
        Die die = new Die(SagradaColor.GREEN);
        grid[0][0].setDie(die);
        assertTrue(AdjacencyConstraint.checkCellAdjacency(grid, 1, 1));
        grid[1][3].setDie(die);
        assertTrue(AdjacencyConstraint.checkCellAdjacency(grid, 2, 3));
        grid[2][2].setDie(die);
        grid[3][1].setDie(die);
        assertTrue(AdjacencyConstraint.checkCellAdjacency(grid, 3, 2));
    }

    @Test
    void checkEmptyCell() {
        Cell[][] grid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
        grid[3][4] = new Cell();
        assertTrue(AdjacencyConstraint.checkEmptyCell(grid, 3, 4));
        Die die = new Die(SagradaColor.GREEN);
        grid[3][4].setDie(die);
        assertFalse(AdjacencyConstraint.checkEmptyCell(grid, 3, 4));
    }
}