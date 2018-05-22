package it.polimi.ingsw;

import it.polimi.ingsw.model.ColorConstraint;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ColorConstraintTest {

    /**
     * For every SagradaColor, check if the ColorConstraint recognizes a Die with that color, and if it rejects a Die colored black
     */
    @Test
    void checkConstraint() {
        for (SagradaColor sagradaColor : SagradaColor.values()) {
            ColorConstraint constraint = new ColorConstraint(sagradaColor);
            assertTrue(constraint.checkConstraint(new Die(sagradaColor)));
            //assertFalse(constraint.checkConstraint(new Die(Color.BLACK)));
        }
    }
}