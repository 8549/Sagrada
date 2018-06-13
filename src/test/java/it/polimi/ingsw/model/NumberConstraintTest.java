package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CheckModifier;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.NumberConstraint;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberConstraintTest {

    @Test
    void checkConstraint() {
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
}