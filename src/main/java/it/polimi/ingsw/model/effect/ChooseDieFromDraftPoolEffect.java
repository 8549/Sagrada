package it.polimi.ingsw.model.effect;

public class ChooseDieFromDraftPoolEffect extends Effect {

    public ChooseDieFromDraftPoolEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromDraftPool();
    }
}
