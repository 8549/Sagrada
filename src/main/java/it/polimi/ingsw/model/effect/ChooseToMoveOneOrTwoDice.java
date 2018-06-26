package it.polimi.ingsw.model.effect;

public class ChooseToMoveOneOrTwoDice extends Effect{

    @Override
    public void perform(Object... args) {
        toolCard.chooseToMoveOneDie();
    }
}
