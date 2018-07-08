package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Round;

public class CheckIsFirstTurnEffect extends Effect{

    public CheckIsFirstTurnEffect(String name){
        this.name = name;
    }

    /**
     * If it's the first turn sets everythingOk to true, false otherwise
     * @param args round
     */
    @Override
    public void perform(Object... args) {
        Round round = (Round) args[0];
        if (round.getTurn().getNumber()== 1){
            toolCard.setResponse(true);
        } else {
            toolCard.setResponse(false);
        }
    }
}
