package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Player;

public class CheckIfThereIsAtLeastTwoDieOnWindowPatterEffect extends Effect {

    public CheckIfThereIsAtLeastTwoDieOnWindowPatterEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Player player = (Player) args[0];
        if (player.getPlayerWindow().dieCount()>1){
            toolCard.setResponse(true);
        } else {
            toolCard.setResponse(false);
        }
    }
}
