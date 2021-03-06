package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrivateObjectiveCardTest {

    @Test
    void testCheckObjective() {
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
        String name = "Shades of Red";
        CardsDeck privateObjectiveCardsDeck = new CardsDeck("PrivateObjectiveCards.json", new TypeToken<List<PrivateObjectiveCard>>() {
        }.getType());
        Card card = privateObjectiveCardsDeck.getByName(name);
        PrivateObjectiveCard privateObjectiveCard = (PrivateObjectiveCard)card;
        assertEquals(17, privateObjectiveCard.checkObjective(grid));
        Card card2 = privateObjectiveCardsDeck.getByName("Shades of Green");
        Card card1 = privateObjectiveCardsDeck.getByName("Shades of Blue");
        Card card3 = privateObjectiveCardsDeck.getByName("Shades of Purple");
        Card card4 = privateObjectiveCardsDeck.getByName("Shades of Yellow");
        PrivateObjectiveCard privateObjectiveCard1 = (PrivateObjectiveCard)card1;
        PrivateObjectiveCard privateObjectiveCard2 = (PrivateObjectiveCard)card2;
        PrivateObjectiveCard privateObjectiveCard3 = (PrivateObjectiveCard)card3;
        PrivateObjectiveCard privateObjectiveCard4 = (PrivateObjectiveCard)card4;
        assertEquals(14, privateObjectiveCard1.checkObjective(grid));
        assertEquals(14, privateObjectiveCard2.checkObjective(grid));
        assertEquals(14, privateObjectiveCard3.checkObjective(grid));
        assertEquals(8, privateObjectiveCard4.checkObjective(grid));



    }


}