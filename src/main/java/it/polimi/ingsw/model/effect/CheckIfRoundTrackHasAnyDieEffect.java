package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;

public class CheckIfRoundTrackHasAnyDieEffect extends Effect {

    public CheckIfRoundTrackHasAnyDieEffect(String name) {
        this.name = name;
    }

    /**
     * If it's the first round sets everythingOk to false, true otherwise
     * @param args board
     */
    @Override
    public void perform(Object... args) {
        Board board = (Board) args[0];
        if (board.getRoundTrack().getRoundCounter() == 0) {
            toolCard.setResponse(false);
        } else {
            toolCard.setResponse(true);
        }

    }
}
