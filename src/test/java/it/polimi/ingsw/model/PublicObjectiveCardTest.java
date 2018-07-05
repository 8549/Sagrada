package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PublicObjectiveCardTest {


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
        String name = "Color Diagonals";
        CardsDeck publicObjectiveCardsDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
        }.getType());
        Card card = publicObjectiveCardsDeck.getByName(name);
        PublicObjectiveCard publicObjectiveCard = (PublicObjectiveCard)card;

        assertEquals(16, publicObjectiveCard.checkObjective(grid));

        Card card1= publicObjectiveCardsDeck.getByName("Color Variety");
        PublicObjectiveCard publicObjectiveCard1 = (PublicObjectiveCard)card1;
        assertEquals(12,publicObjectiveCard1.checkObjective(grid));
        Card card2= publicObjectiveCardsDeck.getByName("Shade Variety");
        PublicObjectiveCard publicObjectiveCard2 = (PublicObjectiveCard)card2;
        assertEquals(15,publicObjectiveCard2.checkObjective(grid) );
        Card card3= publicObjectiveCardsDeck.getByName("Deep Shades");
        PublicObjectiveCard publicObjectiveCard3 = (PublicObjectiveCard)card3;
        assertEquals(6, publicObjectiveCard3.checkObjective(grid));
        Card card4= publicObjectiveCardsDeck.getByName("Medium Shades");
        PublicObjectiveCard publicObjectiveCard14 = (PublicObjectiveCard)card4;
        assertEquals(6, publicObjectiveCard14.checkObjective(grid));
        Card card15= publicObjectiveCardsDeck.getByName("Light Shades");
        PublicObjectiveCard publicObjectiveCard15 = (PublicObjectiveCard)card15;
        assertEquals(6, publicObjectiveCard15.checkObjective(grid));
        Card card18= publicObjectiveCardsDeck.getByName("Column Shade Variety");
        PublicObjectiveCard publicObjectiveCard18 = (PublicObjectiveCard)card18;
        assertEquals(12, publicObjectiveCard18.checkObjective(grid));
        Card card19= publicObjectiveCardsDeck.getByName("Row Shade Variety");
        PublicObjectiveCard publicObjectiveCard19 = (PublicObjectiveCard)card19;
        assertEquals(10, publicObjectiveCard19.checkObjective(grid));
        Card card10= publicObjectiveCardsDeck.getByName("Column Color Variety");
        PublicObjectiveCard publicObjectiveCard10 = (PublicObjectiveCard)card10;
        assertEquals(15, publicObjectiveCard10.checkObjective(grid));
        Card card11= publicObjectiveCardsDeck.getByName("Row Color Variety");
        PublicObjectiveCard publicObjectiveCard11 = (PublicObjectiveCard)card11;
        assertEquals(0,publicObjectiveCard11.checkObjective(grid));
    }

}