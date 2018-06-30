package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class RollDieEffect extends Effect{

    public RollDieEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        die.roll();
        toolCard.setResponse(true);
    }
}
