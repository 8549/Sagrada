package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class ChangeTurnOrderEffect extends Effect {

    public ChangeTurnOrderEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Round round = (Round) args[0];
        round.doubledTurn(round.getCurrentTurn());
        toolCard.getToolCardHandler().notifyChangeTurn(round.getTurn().getPlayer());
        toolCard.setResponse(true);
    }
}
