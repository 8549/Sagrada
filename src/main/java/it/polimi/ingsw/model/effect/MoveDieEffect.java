package it.polimi.ingsw.model.effect;


public class MoveDieEffect extends Effect{

    public MoveDieEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.processTwoMoveWithoutConstraints(true, true, true, false);
    }
}
