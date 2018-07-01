package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.*;

public class PlaceDieEffect extends Effect {

    public PlaceDieEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Turn turn = (Turn) args[0];
        boolean placeDie = (boolean) args[1];
        if (turn.isDiePlaced()) {
            toolCard.getToolCardHandler().notifyPlayerDieAlreadyPlaced();
        } else if (placeDie) {
            toolCard.processMoveWithoutConstraints(true, true, true, true);
        }
    }
}
