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
        Die die1 = toolCard.getBoard().getRoundTrack().replaceDie(die, turn, numberOfDie);
        if(die1!=null) {
            toolCard.getBoard().getDraftPool().add(die1);
            toolCard.getBoard().getDraftPool().remove(die);
            toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
            toolCard.getToolCardHandler().updateRoundTrack(die, numberOfDie, turn);
            toolCard.setResponse(true);
        } else{
            toolCard.setResponse(false);
        }
    }
}
