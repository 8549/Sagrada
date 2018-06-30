package it.polimi.ingsw.model.effect;

public class ChooseDieFromWindowPattern extends Effect {

    public ChooseDieFromWindowPattern(String name){
        this.name = name;
    }
    @Override
    public void perform(Object... args) {
        toolCard.chooseDieFromWindowPattern();
    }
}
