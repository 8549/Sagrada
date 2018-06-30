package it.polimi.ingsw.model.effect;

public class ChooseIfDecreaseOrIncreaseValue extends Effect{

    public ChooseIfDecreaseOrIncreaseValue(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseIfDecrease();
    }
}
