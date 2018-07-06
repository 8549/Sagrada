package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Round;

public class RollAllDiceEffect extends Effect {

    public RollAllDiceEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Round round = (Round) args[0];
        for (Die die : round.getDraftPool()) {
            die.roll();
        }
        toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
        toolCard.setResponse(true);
    }
}
