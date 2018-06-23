package it.polimi.ingsw.model.effect;

public class ChooseDieFromDraftPoolEffect extends Effect {
    @Override
    public boolean perform(Object... args) {
        toolCard.chooseDieFromDraftPool();
        return true;
    }
}
