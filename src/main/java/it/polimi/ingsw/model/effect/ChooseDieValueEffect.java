package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class ChooseDieValueEffect extends Effect {

    @Override
    public boolean perform(Object... args) {
        Die die = (Die) args[0];
        toolCard.setValue();
        die.setNumber(toolCard.getValue());
        return true;
    }
}
