package it.polimi.ingsw.model.effect;

public class ChooseIfDecreaseOrIncreaseValue extends Effect{

    @Override
    public void perform(Object... args) {
        toolCard.chooseIfDecrease();
    }
}
