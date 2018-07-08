package it.polimi.ingsw.model.effect;

public class ChooseIfDecreaseOrIncreaseValueEffect extends Effect{

    public ChooseIfDecreaseOrIncreaseValueEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to ask the  player to choose if decrease or increase tthe value of the die
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseIfDecrease();
    }
}
