package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.effect.*;
import it.polimi.ingsw.serialization.GsonSingleton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToolCardTest {

   @org.junit.jupiter.api.Test
    void verifyToolCard(){
        String name = "Tap Wheel";
        int id= 12;
        String when= "always";
        List<Effect> features = new ArrayList<>();
        Effect effect= new ChooseToMoveOneOrTwoDice("chooseToMoveOneOrTwoDice");
        Effect effect1 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
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
}