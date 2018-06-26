package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;

public class PlaceDieInDraftPoolEffect extends Effect{

    @Override
    public void perform(Object... args) {
        boolean placeDie = (boolean) args[0];
        Board board = (Board) args[1];
        Die die = (Die) args[2];
        if(!placeDie){
            board.getDraftPool().add(die);
        }
        toolCard.setResponse(true);
    }
}
