package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class FlipDieEffect extends Effect{

    public FlipDieEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        die.flip();
        toolCard.setResponse(true);
    }
}
