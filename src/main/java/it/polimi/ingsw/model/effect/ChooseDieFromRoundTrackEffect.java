package it.polimi.ingsw.model.effect;

public class ChooseDieFromRoundTrackEffect extends Effect {

    public  ChooseDieFromRoundTrackEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromRoundTrack();
    }
}
