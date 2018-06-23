package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class MoveDieWithotColorConstraintEffect extends Effect{

    @Override
    public boolean perform(Object... args) {
        return toolCard.processMoveWithoutConstraints(true, false, true, false);
    }
}
