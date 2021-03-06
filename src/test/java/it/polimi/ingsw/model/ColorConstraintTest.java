package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testToCLI() {
        ColorConstraint constraint = new ColorConstraint(SagradaColor.BLUE);
        int unicodeNumber = 9632;
        assertEquals(SagradaColor.BLUE.escapeString() + (char) unicodeNumber + SagradaColor.RESET, constraint.toCLI());
    }



    @Test
    void testGetColor() {
        ColorConstraint constraint = new ColorConstraint(SagradaColor.BLUE);
        assertEquals(SagradaColor.BLUE, constraint.getColor());
    }
}