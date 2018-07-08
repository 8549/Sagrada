package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class CheckIsSecondTurnEffect extends Effect{

    public CheckIsSecondTurnEffect(String name){
        this.name = name;
    }

    /**
     * If it's the second turn sets everythingOk to true, false otherwise
     * @param args round : current round
     */
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
