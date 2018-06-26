package it.polimi.ingsw.model.effect;

public class ChooseDieFromWindowPattern extends Effect {
    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromWindowPattern();
    }
}
