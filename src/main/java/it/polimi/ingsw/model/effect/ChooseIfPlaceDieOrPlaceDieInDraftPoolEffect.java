package it.polimi.ingsw.model.effect;

public class ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect extends Effect {

    public ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to ask the  player to choose if the player wants to place the dio
     * on the window pattern or in the draft pool
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseIfPlaceDie();
    }
}
