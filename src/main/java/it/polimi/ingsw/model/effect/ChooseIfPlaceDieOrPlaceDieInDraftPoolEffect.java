package it.polimi.ingsw.model.effect;

public class ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect extends Effect {
    @Override
    public void perform(Object... args) {
        toolCard.chooseIfPlaceDie();
    }
}
