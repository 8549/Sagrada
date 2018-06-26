package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.MoveValidator;
import it.polimi.ingsw.model.Player;

public class PlaceDieEffect extends Effect {
    @Override
    public void perform(Object... args) {
        toolCard.processMoveWithoutConstraints(true, true, true, true);
    }
}
