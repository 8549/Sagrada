package it.polimi.ingsw.model;

import javafx.scene.Node;

public interface PatternConstraint {
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

    boolean checkConstraint(Die die);

    /**
     * Get the appropriate graphic JavaFX Node for the constraint
     *
     * @param size
     * @return a JavaFX Node representing the constraint
     */
    Node getAsGraphic(double size);
}
