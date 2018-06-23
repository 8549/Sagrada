package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PublicObjectiveCardTest {

    @Test
    void testSetOfShades() {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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

        assertEquals(3, publicObjectiveCard.setOfShades(0, 0, grid)); // shades variety test
        assertEquals(3, publicObjectiveCard.setOfShades(1, 2, grid)); // light shades test
        assertEquals(3, publicObjectiveCard.setOfShades(3, 4, grid)); // medium shades test
        assertEquals(3, publicObjectiveCard.setOfShades(5, 6, grid)); // deep shades test
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(2, publicObjectiveCard.setOfShades(0, 0, grid)); // shades variety test
        assertEquals(2, publicObjectiveCard.setOfShades(1, 2, grid)); // light shades test
        assertEquals(2, publicObjectiveCard.setOfShades(3, 4, grid)); // medium shades test
        assertEquals(2, publicObjectiveCard.setOfShades(5, 6, grid)); // deep shades test
    }

    @Test
    void testSetOfColors() {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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

        assertEquals(3, publicObjectiveCard.setOfColors(grid)); // two colors with the same numbers  of repetitions
        Die die = new Die(SagradaColor.YELLOW);
        grid[2][3].setDie(die);
        assertEquals(3, publicObjectiveCard.setOfColors(grid)); // just one color has the minor number of repetitions
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(2, publicObjectiveCard.setOfColors(grid)); // two colors with the same numbers  of repetitions
        grid[2][0].removeDie();
        assertEquals(1, publicObjectiveCard.setOfColors(grid)); // just one color has the minor number of repetitions
    }

    @Test
    void testRowNumberVariety() {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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

        assertEquals(2, publicObjectiveCard.rowNumberVariety(grid));
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(1, publicObjectiveCard.rowNumberVariety(grid));
    }

    @Test
    void testColumnNumberVariety() {

        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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

        assertEquals(3, publicObjectiveCard.columnNumberVariety(grid));
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(2, publicObjectiveCard.columnNumberVariety(grid));
    }

    @Test
    void testRowColorVariety() {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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

        assertEquals(1, publicObjectiveCard.rowColorVariety(grid));
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(0, publicObjectiveCard.rowColorVariety(grid));
    }

    @Test
    void testColumnColorVariety() {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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

        assertEquals(4, publicObjectiveCard.columnColorVariety(grid));
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(1, publicObjectiveCard.columnColorVariety(grid));
    }

    @Test
    void testDiagonalsVariety() {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
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
        dice[13] = new Die(SagradaColor.YELLOW);
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

        assertEquals(16, publicObjectiveCard.diagonalsVariety(grid));
        grid[1][3].removeDie();
        grid[1][4].removeDie();
        grid[2][1].removeDie();
        grid[3][3].removeDie();
        grid[3][4].removeDie();
        assertEquals(10, publicObjectiveCard.diagonalsVariety(grid));
    }

    @Test
    void checkObjective() {
    }

    @Test
    void getPoints() {
    }
}