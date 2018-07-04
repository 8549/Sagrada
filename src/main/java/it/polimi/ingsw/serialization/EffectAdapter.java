package it.polimi.ingsw.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.effect.*;

import java.io.IOException;

public class EffectAdapter extends TypeAdapter<Effect> {

    @Override
    public void write(JsonWriter jsonWriter, Effect e) throws IOException {
        jsonWriter.value(e.getName());
    }

    @Override
    public Effect read(JsonReader jsonReader) throws IOException {
        Effect effect = null;
        String name = jsonReader.nextString();
        switch (name) {
            case "addDieToDicePool":
                effect = new AddDieToDicePoolEffect(name);
                break;
            case "changeTurnOrder":
                effect = new ChangeTurnOrderEffect(name);
                break;
            case "checkIfDieHasBeenPlaced":
                effect = new CheckIfDieHasBeenPlacedEffect(name);
                break;
            case "checkIsDiePlaced":
                effect = new CheckIsDiePlacedEffect(name);
                break;
            case "checkIsFirstTurn":
                effect = new CheckIsFirstTurnEffect(name);
                break;
            case "checkIsSecondTurn":
                effect = new CheckIsSecondTurnEffect(name);
                break;
            case "chooseDieFromDraftPool":
                effect = new ChooseDieFromDraftPoolEffect(name);
                break;
            case "chooseDieFromRoundTrack":
                effect = new ChooseDieFromRoundTrackEffect(name);
                break;
            case "chooseDieFromWindowPattern":
                effect = new ChooseDieFromWindowPattern(name);
                break;
            case "chooseDieValue":
                effect = new ChooseDieValueEffect(name);
                break;
            case "chooseIfDecreaseOrIncreaseValue":
                effect = new ChooseIfDecreaseOrIncreaseValue(name);
                break;
            case "chooseIfPlaceDieOrPlaceDieInDraftPool":
                effect = new ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect(name);
                break;
            case "chooseToMoveOneOrTwoDice":
                effect = new ChooseToMoveOneOrTwoDice(name);
                break;
            case "chooseTwoDiceFromWindowPattern":
                effect = new ChooseTwoDiceFromWindowPatterEffect(name);
                break;
            case "decreaseValueDie":
                effect = new DecreaseValueEffect(name);
                break;
            case "flipDie":
                effect = new FlipDieEffect(name);
                break;
            case "getDieFromDicePool":
                effect = new GetDieFromDicePoolEffect(name);
                break;
            case "increaseValueDie":
                effect = new IncreaseValueEffect(name);
                break;
            case "moveDie":
                effect = new MoveDieEffect(name);
                break;
            case "moveDieWithoutColorConstraint":
                effect = new MoveDieWithoutColorConstraintEffect(name);
                break;
            case "moveDieWithoutNumberConstraint":
                effect = new MoveDieWithoutNumberConstraintEffect(name);
                break;
            case "moveDieWithSameColorAsDieFromRoundTrack":
                effect = new MoveDieWithSameColorAsDieFromRoundTrackEffect(name);
                break;
            case "placeDie":
                effect = new PlaceDieEffect(name);
                break;
            case "placeDieInDraftPool":
                effect = new PlaceDieInDraftPoolEffect(name);
                break;
            case "placeDieWithoutAdjacencyConstraint":
                effect = new PlaceDieWithoutAdjacencyConstraintEffect(name);
                break;
            case "replaceDieOnRoundTrack":
                effect = new ReplaceDieOnRoundTrackEffect(name);
                break;
            case "rollAllDice":
                effect = new RollAllDiceEffect(name);
                break;
            case "rollDie":
                effect = new RollDieEffect(name);
                break;
        }
        return effect;
    }
}
