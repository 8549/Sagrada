package it.polimi.ingsw;

import javafx.scene.paint.Color;
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
            Color color = SagradaColor.values()[colorIndex].getColor();
            Die die = new Die(color);
            NumberConstraint constraint = new NumberConstraint(i);
            if (die.getNumber() == i) {
                assertTrue(constraint.checkConstraint(die));
            } else {
                assertFalse(constraint.checkConstraint(die));
            }
        }
    }
}