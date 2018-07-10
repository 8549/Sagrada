package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class ChangeTurnOrderEffect extends Effect {

    public ChangeTurnOrderEffect(String name){
        this.name = name;
    }

    /**
     * Changes the order of the turns, notifies the players, as sets as used the tool card of the next turn
     * @param args round: the current round
     */
    @Override
    public void perform(Object... args) {
        Round round = (Round) args[0];
        round.doubledTurn(round.getCurrentTurn());
        toolCard.getRound().getTurns().get((toolCard.getRound().getCurrentTurn())+1).setToolCardUsed();
        toolCard.getToolCardHandler().notifyChangeTurn(round.getTurn().getPlayer());
        toolCard.getGameManager().endCurrentTurn();
        toolCard.setResponse(true);

    }
}
