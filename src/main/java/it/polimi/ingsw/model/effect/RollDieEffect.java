package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class RollDieEffect extends Effect{
    @Override
    public boolean perform(Object... args) {
        Die die = (Die) args[0];
        die.roll();
        return true;
    }
}
