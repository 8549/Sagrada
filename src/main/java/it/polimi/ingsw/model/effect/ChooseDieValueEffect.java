package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class ChooseDieValueEffect extends Effect {

    public ChooseDieValueEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to ask the  player to choose the value of the given die
     * @param args die
     */
    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        toolCard.setValue();
    }
}
