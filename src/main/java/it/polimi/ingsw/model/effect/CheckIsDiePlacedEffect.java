package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Turn;

public class CheckIsDiePlacedEffect extends Effect{

    @Override
    public boolean perform(Object... args) {
        Turn turn = (Turn) args[0];
        if(!turn.isDiePlaced()){
            return true;
        }
        return false;
    }

}
