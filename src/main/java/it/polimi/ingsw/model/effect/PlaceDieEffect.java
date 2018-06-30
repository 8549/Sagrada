package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.MoveValidator;
import it.polimi.ingsw.model.Player;

public class PlaceDieEffect extends Effect {

    public PlaceDieEffect (String name){
        this.name = name;
    }

        @Override
    public void perform(Object... args) {
        toolCard.processMoveWithoutConstraints(true, true, true, true);
    }
}
