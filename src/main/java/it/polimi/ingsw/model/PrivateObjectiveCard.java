package it.polimi.ingsw.model;

import java.io.Serializable;

public class PrivateObjectiveCard extends ObjCard implements Serializable {

    /**
     * Sums the value of all the dice in the grid with the color of the private objective card
     * @param grid
     * @return the value of all the dice in the grid with the color of the private objective card
     */
    @Override
    public int checkObjective(Cell[][] grid) {
        if (type.equals(ObjectiveCardType.COLOR)) {
        return sumColors(grid, ((ColorConstraint) rules[0]).getColor());
        }
        return 0;
    }
   }

