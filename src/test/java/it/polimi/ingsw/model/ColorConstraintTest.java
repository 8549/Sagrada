package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CheckModifier;
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
    void testCheckConstraint() {
        for (SagradaColor sagradaColor : SagradaColor.values()) {
            ColorConstraint constraint = new ColorConstraint(sagradaColor);
            assertTrue(constraint.checkConstraint(new Die(sagradaColor), CheckModifier.NORMAL));
        }
    }
}