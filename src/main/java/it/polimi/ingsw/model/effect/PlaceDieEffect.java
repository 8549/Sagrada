package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Turn;

public class PlaceDieEffect extends Effect {

    public PlaceDieEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Turn turn = (Turn) args[0];
        boolean placeDie = (boolean) args[1];
        if (placeDie) {
            if (turn.isDiePlaced()) {
                toolCard.getBoard().getDraftPool().add(toolCard.getDie());
                toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
                toolCard.getToolCardHandler().notifyPlayerDieAlreadyPlaced();
                toolCard.setResponse(true);
            } else {
                toolCard.processMoveWithoutConstraints(true, true, true, true);
            }
        } else {
            toolCard.setResponse(true);
            toolCard.checkHasNextEffect();
        }
    }
}
