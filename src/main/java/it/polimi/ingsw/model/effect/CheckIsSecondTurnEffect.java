package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class CheckIsSecondTurnEffect extends Effect{

    @Override
    public void perform(Object... args) {
        Round round = (Round) args[0];
        if (round.getTurn().getNumber()== 2){
            toolCard.setResponse(true);
        } else {
            toolCard.setResponse(false);
        }
    }
}
