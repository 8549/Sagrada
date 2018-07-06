package it.polimi.ingsw.model;

import it.polimi.ingsw.model.BlankConstraint;
import it.polimi.ingsw.model.CheckModifier;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.Ignore;
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

    @Ignore
    void testGetAsGraphic() {
        Double size= 1.2;
        Rectangle rect = new Rectangle();
        rect.setWidth(size);
        rect.setHeight(size);
        rect.setFill(Color.TRANSPARENT);
        BlankConstraint constraint = new BlankConstraint();
        assertEquals(rect, constraint.getAsGraphic(size));
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

    @Test
    void equals() {
    }
}