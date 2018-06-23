package it.polimi.ingsw.model.effect;

public class ChooseDieFromRoundTrackEffect extends Effect {
    @Override
    public boolean perform(Object... args) {
        toolCard.chooseDieFromRoundTrack();
        return true;
    }
}
