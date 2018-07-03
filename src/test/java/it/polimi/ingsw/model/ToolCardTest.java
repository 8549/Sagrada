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
    void initReferences() {
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
    void useTools() {
    }

    @Test
    void performEffect() {
    }

    @Test
    void checkHasNextEffect() {
    }

    @Test
    void testGetTokens() {
        ToolCard toolCard= new ToolCard();
        toolCard.addTokens();
        toolCard.addTokens();
        assertEquals(2, toolCard.getTokens());
    }

    @Test
    void addDieToDiceBag() {
        ToolCard toolCard= new ToolCard();
        Board board =new Board();
        board.setDiceBag();
        board.getDiceBag().draftDie();
        Die die =board.getDiceBag().draftDie();
        toolCard.addDieToDiceBag(die);
        //assertTrue(j);
    }

    @Test
    void processMoveWithoutConstraints() {
    }

    @Test
    void getDieFromDicePool() {
    }

    @Test
    void chooseDieFromWindowPattern() {
    }

    @Test
    void chooseDieFromDraftPool() {
    }

    @Test
    void chooseDieFromRoundTrack() {
    }

    @Test
    void chooseIfDecrease() {
    }

    @Test
    void chooseIfPlaceDie() {
    }

    @Test
    void chooseToMoveOneDie() {
    }

    @Test
    void setValue() {
    }

    @Test
    void setOldCoordinates() {
    }

    @Test
    void setNewCoordinates() {
    }

    @Test
    void completeProcessMove() {
    }

    @Test
    void completeChooseDieFromWindowPattern() {
    }

    @Test
    void setResponse() {
    }

    @Test
    void completeChooseValue() {
        ToolCard toolCard= new ToolCard();
        toolCard.completeChooseValue(2);
        //assertTrue(toolCard.isEverythingOk());
    }

    @Test
    void setParameters() {
    }

    @Test
    void getToolCardHandler() {
    }
}