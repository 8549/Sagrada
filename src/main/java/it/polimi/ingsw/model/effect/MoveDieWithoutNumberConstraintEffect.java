package it.polimi.ingsw.model.effect;

public class MoveDieWithoutNumberConstraintEffect extends Effect {

    public MoveDieWithoutNumberConstraintEffect(String name){
        this.name = name;
    }

    /**
     * Asks the tool card to move the die without number ccnstraint if it's possible
     * @param args
     */
    @Override
    public void perform(Object... args) {
       toolCard.processMoveWithoutConstraints(false, true, true, false);

    }
}
