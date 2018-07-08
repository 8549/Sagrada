package it.polimi.ingsw.model.effect;

public class MoveDieWithoutColorConstraintEffect extends Effect{

    public MoveDieWithoutColorConstraintEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.processMoveWithoutConstraints(true, false, true, false);
    }
}
