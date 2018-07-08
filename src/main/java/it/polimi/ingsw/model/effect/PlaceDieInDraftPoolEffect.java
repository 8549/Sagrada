package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;

public class PlaceDieInDraftPoolEffect extends Effect{

    public PlaceDieInDraftPoolEffect(String name){
        this.name = name;
    }

    /**
     * Asks the tool card to place the die on the wroundtrack if placeDie if true and if it's possible
     * otherwise asks if the tool card has another effect
     * @param args  placeDie:if the die needs to be placed , board, die
     */
    @Override
    public void perform(Object... args) {
        boolean placeDie = (boolean) args[0];
        Board board = (Board) args[1];
        Die die = (Die) args[2];
        if(!placeDie){
            board.getDraftPool().add(die);
            toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
        }
        toolCard.setResponse(true);
    }
}
