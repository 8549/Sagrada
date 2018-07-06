package it.polimi.ingsw.model.effect;

public class ChooseIfDecreaseOrIncreaseValueEffect extends Effect{

    public ChooseIfDecreaseOrIncreaseValueEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseIfDecrease();
    }
}
