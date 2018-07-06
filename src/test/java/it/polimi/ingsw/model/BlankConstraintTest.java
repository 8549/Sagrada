package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlankConstraintTest {

    @Test
    void testCheckConstraint() {
        BlankConstraint constraint = new BlankConstraint();
        for (SagradaColor sagradaColor : SagradaColor.values()) {
            for (int i = Die.MIN; i <= Die.MAX; i++) {
                assertTrue(constraint.checkConstraint(new Die(sagradaColor), CheckModifier.NORMAL));
            }
        }
    }


    @Test
    void testToCLI() {
        BlankConstraint constraint = new BlankConstraint();
        assertEquals(" ", constraint.toCLI());
    }

    @Test
    void testToString() {
        BlankConstraint constraint = new BlankConstraint();
        assertEquals("BLANK", constraint.toString());

    }


}