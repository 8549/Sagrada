package it.polimi.ingsw.model.effect;

public class ChooseToMoveOneOrTwoDice extends Effect{

    public ChooseToMoveOneOrTwoDice(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseToMoveOneDie();
    }
}
