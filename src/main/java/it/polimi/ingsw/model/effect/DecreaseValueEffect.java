package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class DecreaseValueEffect extends Effect {

    @Override
    public boolean perform(Object... args) {
        Die die = (Die) args[0];
        boolean decrease = (boolean) args[1];
        if (decrease) {
            die.decrease();
        }
        return true;
    }
}
