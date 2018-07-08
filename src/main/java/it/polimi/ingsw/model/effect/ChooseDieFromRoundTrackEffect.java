package it.polimi.ingsw.model.effect;

public class ChooseDieFromRoundTrackEffect extends Effect {

    public  ChooseDieFromRoundTrackEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to ask the  player to choose a die from the roundtrack
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromRoundTrack();
    }
}
