package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjCardTest {

    @Test
    void testGetName() {
        String name = "Row Color Variety";
        CardsDeck objDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
        }.getType());
        Card expectedCard = objDeck.getByName(name);
        assertEquals(name, expectedCard.getName());
    }
}