package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Turn;

public class CheckIfDieHasBeenPlacedEffect extends Effect {
    public CheckIfDieHasBeenPlacedEffect(String name){
        this.name = name;
    }

    /**
     * if at the given turn the die is placed sets everythingOk to true, false otherwise
     * @param args turn : the current turn
     */
    @Override
    public void perform(Object... args) {
        Turn turn = (Turn) args[0];
        if(turn.isDiePlaced()){
            toolCard.setResponse(true);
        } else {
            toolCard.setResponse(false);
        }
    }
}
