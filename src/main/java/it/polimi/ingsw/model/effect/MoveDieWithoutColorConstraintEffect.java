package it.polimi.ingsw.model.effect;

public class MoveDieWithoutColorConstraintEffect extends Effect{

    public MoveDieWithoutColorConstraintEffect(String name) {
        this.name = name;
    }

    /**
     * Asks the tool card to move the die without color ccnstraint if it's possible
     * @param args
     */
    @Override
    public void perform(Object... args) {
        toolCard.processMoveWithoutConstraints(true, false, true, false);
    }
}
