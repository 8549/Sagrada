package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Turn;

public class PlaceDieEffect extends Effect {

    public PlaceDieEffect(String name) {
        this.name = name;
    }

    /**
     * Asks the tool card to place the die on the window pattern if placeDie if true and if it's possible
     * otherwise asks if the tool card has another effect
     * @param args turn : current turn, placeDie:if the die needs to be placed on the window pattern
     */
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
