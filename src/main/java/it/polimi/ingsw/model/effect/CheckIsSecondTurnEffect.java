package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class CheckIsSecondTurnEffect extends Effect{

    @Override
    public boolean perform(Object... args) {
        Round round = (Round) args[0];
        if (round.getTurn().getNumber()== 2){
            return true;
        }
        return false;
    }
}
