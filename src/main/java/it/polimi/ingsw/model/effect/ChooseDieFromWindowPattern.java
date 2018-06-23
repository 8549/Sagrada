package it.polimi.ingsw.model.effect;

public class ChooseDieFromWindowPattern extends Effect {
    @Override
    public boolean perform(Object... args) {
        toolCard.chooseDieFromWindowPattern();
        return true;
    }
}
