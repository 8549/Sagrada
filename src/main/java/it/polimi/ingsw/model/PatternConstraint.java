package it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import javafx.scene.Node;

import java.io.Serializable;

public interface PatternConstraint extends Serializable {
    static boolean equals(PatternConstraint[][] one, PatternConstraint[][] two) {
        if (one.length != two.length) {
            return false;
        }
        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < one[i].length; j++) {
                if (!PatternConstraint.equals(one[i][j], two[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean equals(PatternConstraint one, PatternConstraint two) {
        if (one instanceof BlankConstraint && two instanceof BlankConstraint) {
            return true;
        }
        if (one instanceof NumberConstraint && two instanceof NumberConstraint) {
            return (((NumberConstraint) one).getNumber() == ((NumberConstraint) two).getNumber());
        }
        if (one instanceof ColorConstraint && two instanceof ColorConstraint) {
            return ((ColorConstraint) one).getColor().equals(((ColorConstraint) two).getColor());
        }
        return false;
    }

    /**
     * Checks if the die abides by the constraint
     * @param die
     * @param modifier
     * @return true if it does, false otherwise
     */
    boolean checkConstraint(Die die, CheckModifier modifier);

    /**
     * Get the appropriate graphic JavaFX Node for the constraint
     *
     * @param size
     * @return a JavaFX Node representing the constraint
     */
    Node getAsGraphic(double size);

    JsonElement getAsJson();

    /**
     * Gets a string representation of the constraint
     *
     * @return
     */
    String toCLI();
}
