package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Die;

public class MoveDieWithSameColorAsDieFromRoundTrackEffect extends Effect {

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        int turn = (int) args[1];
        int numberOfDie = (int) args[2];
        Board board = (Board) args[3];
        if (die.getColor() == board.getRoundTrack().getDieAt(turn, numberOfDie).getColor()){
            toolCard.processMoveWithoutConstraints(true, true, true, false);
        }
    }
}
