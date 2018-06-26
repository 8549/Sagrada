package it.polimi.ingsw.model.effect;

public class ChooseDieFromRoundTrackEffect extends Effect {
    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromRoundTrack();
    }
}
