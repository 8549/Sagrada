package it.polimi.ingsw.model.effect;

public class ChooseDieFromWindowPatternEffect extends Effect {

    public ChooseDieFromWindowPatternEffect(String name){
        this.name = name;
    }

    /**
     * Asks the tool card to ask the player to choose a die from the window pattern
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromWindowPattern();
    }
}
