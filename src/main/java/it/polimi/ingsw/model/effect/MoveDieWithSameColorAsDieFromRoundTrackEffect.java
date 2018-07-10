package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;

public class MoveDieWithSameColorAsDieFromRoundTrackEffect extends Effect {

    public MoveDieWithSameColorAsDieFromRoundTrackEffect(String name) {
        this.name = name;
    }

    /**
     * Asks the tool card to move the die with the same color of the one chosen from the roundtrack if it's possible
     * @param args
     */
    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        int turn = (int) args[1];
        int numberOfDie = (int) args[2];
        Board board = (Board) args[3];
        if (toolCard.getBoard().getRoundTrack().getDieAt(turn, numberOfDie) == null) {
            toolCard.setResponse(false);
        } else {
            if (die.getColor().equals(board.getRoundTrack().getDieAt(turn, numberOfDie).getColor())) {
                toolCard.processMoveWithoutConstraints(true, true, true, false);
            } else {
                toolCard.setResponse(false);
            }
        }
    }
}
