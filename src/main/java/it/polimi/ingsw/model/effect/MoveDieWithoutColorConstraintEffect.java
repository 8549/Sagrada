package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class MoveDieWithoutColorConstraintEffect extends Effect{

    public MoveDieWithoutColorConstraintEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.processMoveWithoutConstraints(true, false, true, false);
    }
}
