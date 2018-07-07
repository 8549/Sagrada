package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;


public class ReplaceDieOnRoundTrackEffect extends Effect {

    public ReplaceDieOnRoundTrackEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        int turn = (int) args[1];
        int numberOfDie = (int) args[2];
        if (toolCard.getBoard().getRoundTrack().getDieAt(turn, numberOfDie) == null) {
            toolCard.setResponse(false);
        } else {
            toolCard.getBoard().getDraftPool().add(toolCard.getBoard().getRoundTrack().replaceDie(die, turn, numberOfDie));
            toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
            toolCard.getToolCardHandler().updateRoundTrack(die, numberOfDie, turn);
            toolCard.setResponse(true);
        }
    }
}
