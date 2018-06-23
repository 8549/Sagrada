package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;

public class MoveDieWithoutNumberConstraintEffect extends Effect {
    @Override
    public boolean perform(Object... args) {
       return toolCard.processMoveWithoutConstraints(false, true, true, false);
    }
}
