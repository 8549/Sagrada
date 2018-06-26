package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class ChangeTurnOrderEffect extends Effect {

    @Override
    public void perform(Object... args) {
        Round round = (Round) args[0];
        round.doubledTurn(round.getCurrentTurn());
        toolCard.setResponse(true);
    }
}
