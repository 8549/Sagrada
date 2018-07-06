package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardsDeckTest {

    @Test
    void testGetRandomCard() {
        CardsDeck expectedDeck = new CardsDeck("SamplePatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());
        List<PatternCard> actualCards = getSamplePatternCards();
        assertEquals(expectedDeck.getAsList(), actualCards);
    }

    private List<PatternCard> getSamplePatternCards() {
        List<PatternCard> cards = new ArrayList<>();
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

        patternConstraints[0][0] = new NumberConstraint(2);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(5);
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[1][1] = new NumberConstraint(6);
        patternConstraints[1][2] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[1][3] = new NumberConstraint(2);
        patternConstraints[1][4] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[2][2] = new NumberConstraint(4);
        patternConstraints[2][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new BlankConstraint();
        patternConstraints[3][1] = new NumberConstraint(3);
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new NumberConstraint(4);
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern symphonyOfLight = new WindowPattern(6, "Symphony of Light", patternConstraints);

        PatternCard card1 = new PatternCard(virtus, symphonyOfLight);

        patternConstraints[0][0] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[0][1] = new NumberConstraint(6);
        patternConstraints[0][2] = new BlankConstraint();
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(3);
        patternConstraints[1][0] = new NumberConstraint(5);
        patternConstraints[1][1] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[1][2] = new NumberConstraint(3);
        patternConstraints[1][3] = new BlankConstraint();
        patternConstraints[1][4] = new BlankConstraint();
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(2);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[2][3] = new NumberConstraint(1);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new BlankConstraint();
        patternConstraints[3][1] = new NumberConstraint(1);
        patternConstraints[3][2] = new NumberConstraint(5);
        patternConstraints[3][3] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[3][4] = new NumberConstraint(4);
        WindowPattern firmitas = new WindowPattern(5, "Firmitas", patternConstraints);

        patternConstraints[0][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[0][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[0][2] = new BlankConstraint();
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(5);
        patternConstraints[1][3] = new BlankConstraint();
        patternConstraints[1][4] = new NumberConstraint(4);
        patternConstraints[2][0] = new NumberConstraint(3);
        patternConstraints[2][1] = new BlankConstraint();
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][3] = new BlankConstraint();
        patternConstraints[2][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][0] = new NumberConstraint(2);
        patternConstraints[3][1] = new BlankConstraint();
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[3][4] = new ColorConstraint(SagradaColor.YELLOW);
        WindowPattern kaleidoscopicDream = new WindowPattern(4, "Kaleidoscopic Dream", patternConstraints);

        PatternCard card2 = new PatternCard(firmitas, kaleidoscopicDream);

        cards.add(card1);
        cards.add(card2);

        return cards;
    }

    @Test
    void testGetWindowPatternByName() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];        patternConstraints[0][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[0][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[0][2] = new BlankConstraint();
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(5);
        patternConstraints[1][3] = new BlankConstraint();
        patternConstraints[1][4] = new NumberConstraint(4);
        patternConstraints[2][0] = new NumberConstraint(3);
        patternConstraints[2][1] = new BlankConstraint();
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][3] = new BlankConstraint();
        patternConstraints[2][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][0] = new NumberConstraint(2);
        patternConstraints[3][1] = new BlankConstraint();
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[3][4] = new ColorConstraint(SagradaColor.YELLOW);
        WindowPattern kaleidoscopicDream = new WindowPattern(4, "Kaleidoscopic Dream", patternConstraints);

        CardsDeck expectedDeck = new CardsDeck("SamplePatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());

        assertEquals(kaleidoscopicDream, expectedDeck.getWindowPatternByName("Kaleidoscopic Dream"));

    }

    @Test
    void testGetAsList() {
        CardsDeck expectedDeck = new CardsDeck("SamplePatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());

        assertTrue(expectedDeck != null);
    }
}