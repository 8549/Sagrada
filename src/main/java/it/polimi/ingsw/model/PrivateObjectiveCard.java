package it.polimi.ingsw.model;

import java.awt.*;
import java.io.Serializable;

public class PrivateObjectiveCard extends ObjCard implements Serializable {

    @Override
    public int checkObjective(Cell[][] grid) {
        if (type.equals(ObjectiveCardType.COLOR)) {
        return sumColors(grid, ((ColorConstraint) rules[0]).getColor());
        }
        return 0;
    }
   }

