package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.effect.*;
import it.polimi.ingsw.serialization.GsonSingleton;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToolCardTest {
    @Test
    void testVerifyToolCard(){
        String name = "Tap Wheel";
        int id= 12;
        String when= "always";
        List<Effect> features = new ArrayList<>();
        Effect effect= new ChooseToMoveOneOrTwoDice("chooseToMoveOneOrTwoDice");
        Effect effect1;
        effect1 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
        Effect effect2 = new ChooseDieFromWindowPattern("chooseDieFromWindowPattern");
        Effect effect3 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
        features.add(effect1);
        features.add(effect2);
        features.add(effect3);
        features.add(effect);
        features.add(effect2);
        features.add(effect3);
        ToolCard tapWheel = new ToolCard();
        tapWheel.setParameters(name, id, when, features);
        CardsDeck deck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>(){}.getType());
        Card expectedCard = deck.getByName(name);
        Effect t = GsonSingleton.getInstance().fromJson("moveDieWithoutColorConstraint", Effect.class);
        assertEquals(expectedCard, tapWheel);
    }

    @Test
    void testInitReferences() {
    }

    @Test
    void testIsUsed() {
        ToolCard toolCard= new ToolCard();
        assertFalse(toolCard.isUsed());
    }


    @Test
    void testGetCost() {
        ToolCard toolCard= new ToolCard();
        assertEquals(1, toolCard.getCost());
    }


    @Test
    void testAddTokens() {
        ToolCard toolCard= new ToolCard();
        toolCard.addTokens();
        toolCard.addTokens();
        assertEquals(2, toolCard.getTokens());
    }

    @Test
    void testUseTools() {
    }

    @Test
    void testPerformEffect() {
    }

    @Test
    void testCheckHasNextEffect() {
    }

    @Test
    void testGetTokens() {
        ToolCard toolCard= new ToolCard();
        toolCard.addTokens();
        toolCard.addTokens();
        assertEquals(2, toolCard.getTokens());
    }

    @Test
    void testAddDieToDiceBag() {
        ToolCard toolCard= new ToolCard();
        Board board =new Board();
        board.setDiceBag();
        board.getDiceBag().draftDie();
        Die die =board.getDiceBag().draftDie();
        toolCard.addDieToDiceBag(die);
        //assertTrue(j);
    }

    @Test
    void testProcessMoveWithoutConstraints() {
    }

    @Test
    void testGetDieFromDicePool() {
    }

    @Test
    void testChooseDieFromWindowPattern() {
    }

    @Test
    void testChooseDieFromDraftPool() {
    }

    @Test
    void testChooseDieFromRoundTrack() {
    }

    @Test
    void testChooseIfDecrease() {
    }

    @Test
    void testChooseIfPlaceDie() {
    }

    @Test
    void testChooseToMoveOneDie() {
    }

    @Test
    void testSetValue() {
    }

    @Test
    void tesSetOldCoordinates() {
    }

    @Test
    void testSetNewCoordinates() {
    }

    @Test
    void testCompleteProcessMove() {
    }

    @Test
    void testCompleteChooseDieFromWindowPattern() {
    }

    @Test
    void testSetResponse() {
    }

    @Test
    void testCompleteChooseValue() {
        ToolCard toolCard= new ToolCard();
        toolCard.completeChooseValue(2);
        //assertTrue(toolCard.isEverythingOk());
    }

    @Test
    void testSetParameters() {
    }

    @Test
    void testGetToolCardHandler() {
    }
}