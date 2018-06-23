package it.polimi.ingsw.model.effect;

public class ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect extends Effect {
    @Override
    public boolean perform(Object... args) {
        toolCard.chooseIfPlaceDie();
        return true;
    }
}
