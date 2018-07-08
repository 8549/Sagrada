package it.polimi.ingsw.model.effect;

public class ChooseTwoDiceFromWindowPatterEffect extends Effect {
    public ChooseTwoDiceFromWindowPatterEffect(String name) {
        this.name = name;
    }


    /**
     * Ask the tool card to ask the  player to choose to choose two dice from the window pattern
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseTwoDieFromWindowPatter();
    }
}
