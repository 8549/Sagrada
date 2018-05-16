package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrivateObjectiveCardTest {

    @Test
    void sumColors() {
        PrivateObjectiveCard privateObjectiveCard = new PrivateObjectiveCard();
        Cell[][] grid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
        Die dice[] = new Die[20];
        dice[0] = new Die(SagradaColor.RED);
        dice[0].setNumber(1);
        dice[1] = new Die(SagradaColor.GREEN);
        dice[1].setNumber(5);
        dice[2] = new Die(SagradaColor.RED);
        dice[2].setNumber(6);
        dice[3] = new Die(SagradaColor.YELLOW);
        dice[3].setNumber(3);
        dice[4] = new Die(SagradaColor.PURPLE);
        dice[4].setNumber(2);
        dice[5] = new Die(SagradaColor.GREEN);
        dice[5].setNumber(2);
        dice[6] = new Die(SagradaColor.RED);
        dice[6].setNumber(4);
        dice[7] = new Die(SagradaColor.YELLOW);
        dice[7].setNumber(3);
        dice[8] = new Die(SagradaColor.PURPLE);
        dice[8].setNumber(2);
        dice[9] = new Die(SagradaColor.RED);
        dice[9].setNumber(4);
        dice[10] = new Die(SagradaColor.BLUE);
        dice[10].setNumber(3);
        dice[11] = new Die(SagradaColor.YELLOW);
        dice[11].setNumber(2);
        dice[12] = new Die(SagradaColor.PURPLE);
        dice[12].setNumber(4);
        dice[13] = new Die(SagradaColor.RED);
        dice[13].setNumber(1);
        dice[14] = new Die(SagradaColor.GREEN);
        dice[14].setNumber(5);
        dice[15] = new Die(SagradaColor.PURPLE);
        dice[15].setNumber(6);
        dice[16] = new Die(SagradaColor.BLUE);
        dice[16].setNumber(5);
        dice[17] = new Die(SagradaColor.RED);
        dice[17].setNumber(1);
        dice[18] = new Die(SagradaColor.GREEN);
        dice[18].setNumber(2);
        dice[19] = new Die(SagradaColor.BLUE);
        dice[19].setNumber(6);
        int w = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                grid[i][j] = new Cell();
                grid[i][j].setDie(dice[w]);
                w++;
            }
        }
        assertEquals(17,privateObjectiveCard.sumColors(grid, SagradaColor.RED));
        assertEquals(14, privateObjectiveCard.sumColors(grid, SagradaColor.GREEN));
        assertEquals(8, privateObjectiveCard.sumColors(grid, SagradaColor.YELLOW) );
        assertEquals(14, privateObjectiveCard.sumColors(grid, SagradaColor.PURPLE));
        assertEquals(14, privateObjectiveCard.sumColors(grid, SagradaColor.BLUE));
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(13, privateObjectiveCard.sumColors(grid, SagradaColor.RED) );
        assertEquals(12, privateObjectiveCard.sumColors(grid, SagradaColor.GREEN));
        assertEquals(6, privateObjectiveCard.sumColors(grid, SagradaColor.YELLOW) );
        assertEquals(12, privateObjectiveCard.sumColors(grid, SagradaColor.PURPLE));
        assertEquals(8, privateObjectiveCard.sumColors(grid, SagradaColor.BLUE));
    }

    @Test
    void checkObjective() {
    }

    @Test
    void getPoints() {
    }
}