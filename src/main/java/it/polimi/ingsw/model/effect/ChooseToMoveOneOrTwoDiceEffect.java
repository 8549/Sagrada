package it.polimi.ingsw.model.effect;

public class ChooseToMoveOneOrTwoDiceEffect extends Effect{

    public ChooseToMoveOneOrTwoDiceEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseToMoveOneDie();
    }
}
