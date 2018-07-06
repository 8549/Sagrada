package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberConstraintTest {

    @Test
    void testCheckConstraint() {
        for (int i = Die.MIN; i <= Die.MAX; i++) {
            Random rnd = new Random();
            int colorIndex = rnd.nextInt(SagradaColor.values().length - 1);
            SagradaColor color = SagradaColor.values()[colorIndex];
            Die die = new Die(color);
            NumberConstraint constraint = new NumberConstraint(i);
            if (die.getNumber() == i) {
                assertTrue(constraint.checkConstraint(die, CheckModifier.NORMAL));
            } else {
                assertFalse(constraint.checkConstraint(die, CheckModifier.NORMAL));
            }
        }
    }

    @Test
    void testToCLI() {
        NumberConstraint constraint = new NumberConstraint(3);
        assertEquals("" + 3, constraint.toCLI());
    }

    @Test
    void testGetNumber() {
        NumberConstraint constraint = new NumberConstraint(3);
        assertEquals(3, constraint.getNumber());
    }
}