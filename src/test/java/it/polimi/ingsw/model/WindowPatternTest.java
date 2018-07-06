package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindowPatternTest {

    @Test
    void testGetDifficulty() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        assertEquals(5, virtus.getDifficulty());
    }

    @Test
    void testGetConstraints() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        assertEquals(patternConstraints, virtus.getConstraints());
    }

    @Test
    void testGetName() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        assertEquals("Virtus", virtus.getName());
    }

    @Test
    void testGetConstraint() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        assertEquals(patternConstraints[3][1], virtus.getConstraint(3, 1));
        assertFalse(patternConstraints[3][1].equals(virtus.getConstraint(3, 2)));
    }

    @Test
    void testToString() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        assertTrue(virtus.equals(virtus));
        String name = "Virtus";
        int difficulty = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("WindowPattern{");
        builder.append("\ndifficulty=").append(difficulty);
        builder.append(",\nname='").append(name);
        builder.append(",\npatternConstraints= [");
        for (PatternConstraint[] patternConstraintRow : patternConstraints) {
            builder.append("\n[");
            for (PatternConstraint patternConstraint : patternConstraintRow) {
                builder.append(patternConstraint.toString()).append(", ");
            }
            builder.append("]\n");
        }
        builder.append("]\n");
        builder.append('}');
        assertEquals(builder.toString(), virtus.toString());

        patternConstraints[0][0] = new NumberConstraint(2);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(5);
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[1][1] = new NumberConstraint(6);
        patternConstraints[1][2] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[1][3] = new NumberConstraint(2);
        patternConstraints[1][4] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[2][2] = new NumberConstraint(4);
        patternConstraints[2][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new BlankConstraint();
        patternConstraints[3][1] = new NumberConstraint(3);
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new NumberConstraint(4);
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern symphonyOfLight = new WindowPattern(6, "Symphony of Light", patternConstraints);

        assertFalse(builder.toString().equals(symphonyOfLight.toString()));

    }

    @Test
    void testEquals() {
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        assertTrue(virtus.equals(virtus));

        patternConstraints[0][0] = new NumberConstraint(2);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(5);
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[1][1] = new NumberConstraint(6);
        patternConstraints[1][2] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[1][3] = new NumberConstraint(2);
        patternConstraints[1][4] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[2][2] = new NumberConstraint(4);
        patternConstraints[2][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new BlankConstraint();
        patternConstraints[3][1] = new NumberConstraint(3);
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new NumberConstraint(4);
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern symphonyOfLight = new WindowPattern(6, "Symphony of Light", patternConstraints);
        assertFalse(virtus.equals(symphonyOfLight));
    }
}