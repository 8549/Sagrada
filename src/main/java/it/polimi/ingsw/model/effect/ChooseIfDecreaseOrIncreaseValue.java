package it.polimi.ingsw.model.effect;

public class ChooseIfDecreaseOrIncreaseValue extends Effect{

    @Override
    public boolean perform(Object... args) {
        toolCard.chooseIfDecrease();
        return true;
    }
}
