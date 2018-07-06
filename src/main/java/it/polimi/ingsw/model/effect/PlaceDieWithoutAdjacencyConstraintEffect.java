package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;

public class PlaceDieWithoutAdjacencyConstraintEffect extends Effect {

    public PlaceDieWithoutAdjacencyConstraintEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        for(Die die2 : toolCard.getBoard().getDraftPool()){
            if (toolCard.getDie().getColor().equals(die2.getColor()) && toolCard.getDie().getNumber()==die2.getNumber()){
                toolCard.getBoard().getDraftPool().remove(toolCard.getDie());
                break;
            }
        }
        toolCard.processMoveWithoutConstraints(true, true, false, true);
    }
}
