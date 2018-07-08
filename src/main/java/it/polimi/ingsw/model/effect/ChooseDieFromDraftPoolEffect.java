package it.polimi.ingsw.model.effect;

public class ChooseDieFromDraftPoolEffect extends Effect {

    public ChooseDieFromDraftPoolEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to ask the  player to choose a die from the draft pool
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromDraftPool();
    }
}
