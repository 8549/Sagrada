package it.polimi.ingsw.model.effect;

public class ChooseToMoveOneOrTwoDiceEffect extends Effect{

    public ChooseToMoveOneOrTwoDiceEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to ask the  player to choose if the player wants to move one or two dice
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseToMoveOneDie();
    }
}
