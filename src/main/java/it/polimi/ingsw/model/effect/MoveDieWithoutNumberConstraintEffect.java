package it.polimi.ingsw.model.effect;

public class MoveDieWithoutNumberConstraintEffect extends Effect {

    public MoveDieWithoutNumberConstraintEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
       toolCard.processMoveWithoutConstraints(false, true, true, false);

    }
}
