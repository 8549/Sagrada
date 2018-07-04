package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.GameManager;
import org.junit.jupiter.api.Test;
import sun.java2d.loops.DrawGlyphListAA;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testSetPlayers() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        Board board  = new Board();
        board.setPlayers(players);
        assertEquals(players, board.getPlayers());
    }

    @Test
    void testGetPlayers() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        Board board  = new Board();
        board.setPlayers(players);
        assertEquals(players, board.getPlayers());
    }

    @Test
    void testGetPublicObjectiveCards() {
        PublicObjectiveCard[] publicObjectiveCards = new PublicObjectiveCard[GameManager.PUBLIC_OBJ_CARDS_NUMBER];
        CardsDeck objDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
        }.getType());
        for (int j = 0; j < GameManager.PUBLIC_OBJ_CARDS_NUMBER; j++) {
            publicObjectiveCards[j] = (PublicObjectiveCard) objDeck.getRandomCard();
        }
        Board board = new Board();
        board.setPublicObjectiveCards(publicObjectiveCards);
        assertEquals(publicObjectiveCards, board.getPublicObjectiveCards());
    }

    @Test
    void testSetPublicObjectiveCards() {
        PublicObjectiveCard[] publicObjectiveCards = new PublicObjectiveCard[GameManager.PUBLIC_OBJ_CARDS_NUMBER];
        CardsDeck objDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
        }.getType());
        for (int j = 0; j < GameManager.PUBLIC_OBJ_CARDS_NUMBER; j++) {
            publicObjectiveCards[j] = (PublicObjectiveCard) objDeck.getRandomCard();
        }
        Board board = new Board();
        board.setPublicObjectiveCards(publicObjectiveCards);
        assertEquals(publicObjectiveCards, board.getPublicObjectiveCards());
    }

    @Test
    void testGetToolCards() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        GameManager gameManager = new GameManager(null, players);
        List<ToolCard> toolCard = new ArrayList<>();
        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
        }.getType());
        for (int i = 0; i < GameManager.TOOL_CARDS_NUMBER; i++) {
            toolCard.add((ToolCard) toolDeck.getRandomCard());

        }
        Board board = new Board();
        board.setToolCards(toolCard);
        assertEquals(toolCard, board.getToolCards());
    }

    @Test
    void testSetToolCards() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        GameManager gameManager = new GameManager(null, players);
        List<ToolCard> toolCard = new ArrayList<>();
        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
        }.getType());
        for (int i = 0; i < GameManager.TOOL_CARDS_NUMBER; i++) {
            toolCard.add((ToolCard) toolDeck.getRandomCard());
        }
        Board board = new Board();
        board.setToolCards(toolCard);
        assertEquals(toolCard, board.getToolCards());
    }

    @Test
    void testGetDraftPool() {
        List<Die> draftPool;
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        draftPool = new ArrayList<>();
        Board board = new Board();
        DiceBag diceBag = new DiceBag();
        for (int i = 0; i < (players.size() * 2 + 1); i++)
            draftPool.add(diceBag.draftDie());
        board.setDraftPool(draftPool);
        assertEquals(draftPool, board.getDraftPool());

    }

    @Test
    void testSetDraftPool() {
        List<Die> draftPool;
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        draftPool = new ArrayList<>();
        Board board = new Board();
        DiceBag diceBag = new DiceBag();
        for (int i = 0; i < (players.size() * 2 + 1); i++)
            draftPool.add(diceBag.draftDie());
        board.setDraftPool(draftPool);
        assertEquals(draftPool, board.getDraftPool());
    }

    @Test
    void testGetRoundTrack() {
        Board board = new Board();
        assertTrue(board.getRoundTrack()== null);
        board.setRoundTrack();
        assertFalse(board.getRoundTrack() == null);
    }

    @Test
    void testSetRoundTrack() {
        Board board = new Board();
        assertTrue(board.getRoundTrack()== null);
        board.setRoundTrack();
        assertFalse(board.getRoundTrack() == null);
    }

    @Test
    void testSetScoreTrack() {
        Board board = new Board();
        assertTrue(board.getScoreTrack()== null);
        board.setScoreTrack();
        assertFalse(board.getScoreTrack() == null);
    }

    @Test
    void testGetScoreTrack() {
        Board board = new Board();
        assertTrue(board.getScoreTrack()== null);
        board.setScoreTrack();
        assertFalse(board.getScoreTrack() == null);
    }

    @Test
    void testGetDiceBag() {
        Board board = new Board();
        board.setDiceBag();
        assertFalse(board.getDiceBag()==null);
        Die die = board.getDiceBag().draftDie();
        int i=board.getDiceBag().getSize();
    }

    @Test
    void testSetDiceBag() {
        Board board = new Board();
        board.setDiceBag();
        assertFalse(board.getDiceBag()==null);
    }
}