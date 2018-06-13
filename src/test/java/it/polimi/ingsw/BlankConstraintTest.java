package it.polimi.ingsw;

import it.polimi.ingsw.model.BlankConstraint;
import it.polimi.ingsw.model.CheckModifier;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlankConstraintTest {

    @Test
    void checkConstraint() {
        BlankConstraint constraint = new BlankConstraint();
        for (SagradaColor sagradaColor : SagradaColor.values()) {
            for (int i = Die.MIN; i <= Die.MAX; i++) {
                assertTrue(constraint.checkConstraint(new Die(sagradaColor), CheckModifier.NORMAL));
            }
        }
    }
}