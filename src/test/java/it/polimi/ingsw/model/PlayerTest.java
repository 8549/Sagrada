package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    @Test
    void testSetPrivateObjectiveCard() {
        Player player = new Player("player");
        CardsDeck privateObjectiveCardsDeck = new CardsDeck("PrivateObjectiveCards.json", new TypeToken<List<PrivateObjectiveCard>>() {
        }.getType());
        player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());
        assertTrue(player.getPrivateObjectiveCard() != null);
    }

    @Test
    void testSetStatus() {
        Player player = new Player("player");
        player.setStatus(PlayerStatus.ACTIVE);
        assertEquals(PlayerStatus.ACTIVE, player.getStatus());
    }

    @Test
    void testGetStatus() {
        Player player = new Player("player");
        player.setStatus(PlayerStatus.ACTIVE);
        assertEquals(PlayerStatus.ACTIVE, player.getStatus());
    }

    @Test
    void testGetPlayerWindow() {
    }

    @Test
    void testGetName() {
        Player player = new Player("player");
        assertEquals("player", player.getName());

    }

    @Test
    void testGetPrivateObjectiveCard() {
        Player player = new Player("player");
        CardsDeck privateObjectiveCardsDeck = new CardsDeck("PrivateObjectiveCards.json", new TypeToken<List<PrivateObjectiveCard>>() {
        }.getType());
        player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());
        assertTrue(player.getPrivateObjectiveCard() != null);
    }

    @Test
    void testGetInitialTokens() {

    }

    @Test
    void testRemoveTokens() {

    }

    @Test
    void testGetChoices() {
        Player player = new Player("player");
        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());
        List<PatternCard> choices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            choices.add((PatternCard) patternCardsDeck.getRandomCard());
        }
        player.setChoices(choices);
        assertEquals(choices, player.getChoices());
    }

    @Test
    void testSetChoices() {
        Player player = new Player("player");
        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());
        List<PatternCard> choices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            choices.add((PatternCard) patternCardsDeck.getRandomCard());
        }
        player.setChoices(choices);
        assertEquals(choices, player.getChoices());
    }

    @Test
    void testHasChosenPatternCard() {
        Player player = new Player("player");
        assertFalse(player.hasChosenPatternCard());
    }

    @Test
    void testSetHasChosenPatternCard() {
        Player player = new Player("player");
        player.setHasChosenPatternCard(true);
        assertTrue(player.hasChosenPatternCard());
    }

    @Test
    void testAddPoints() {
        Player player = new Player("player");
        player.addPoints(30);
        assertEquals(30, player.getPoints());
    }

    @Test
    void testGetPoints() {
        Player player = new Player("player");
        player.addPoints(30);
        assertEquals(30, player.getPoints());
    }

    @Test
    void tesGetTokens() {

    }

}