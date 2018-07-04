package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;

public class PlaceDieWithoutAdjacencyConstraintEffect extends Effect {

    public PlaceDieWithoutAdjacencyConstraintEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.getBoard().getDraftPool().remove(toolCard.getDie());
        toolCard.processMoveWithoutConstraints(true, true, false, false);
    }
}
