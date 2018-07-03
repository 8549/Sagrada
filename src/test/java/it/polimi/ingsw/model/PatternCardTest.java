package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternCardTest {

    @Test
    void testGetFront() {

        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());
        Card expectedCard = patternCardsDeck.getRandomCard();
        assertFalse(((PatternCard)expectedCard).getFront()==null);

    }

    @Test
    void testGetBack() {
        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());
        Card expectedCard = patternCardsDeck.getRandomCard();
        assertFalse(((PatternCard)expectedCard).getBack()==null);
    }
}