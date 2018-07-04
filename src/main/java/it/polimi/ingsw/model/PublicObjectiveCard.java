package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class PublicObjectiveCard extends ObjCard implements Serializable {


    public int checkObjective(Cell[][] grid) {

        switch (where) {
            case COLUMN:
                if (type.equals(ObjectiveCardType.NUMBER)) {
                    numberOfTimes = columnNumberVariety(grid);
                } else if (type.equals(ObjectiveCardType.COLOR)) {
                    numberOfTimes = columnColorVariety(grid);
                }
                break;
            case ROW:
                if (type.equals(ObjectiveCardType.NUMBER)) {
                    numberOfTimes = rowNumberVariety(grid);
                } else if (type.equals(ObjectiveCardType.COLOR)) {
                    numberOfTimes = rowColorVariety(grid);
                }
                break;
            case DIAGONALS:
                return diagonalsVariety(grid);
            case EVERYWHERE:
                if (type.equals(ObjectiveCardType.NUMBER)) {
                    if (rules.length == 6)
                        numberOfTimes = setOfShades(0, 0, grid);
                    else if (rules.length == 2)
                        numberOfTimes = setOfShades(((NumberConstraint) rules[0]).getNumber(), ((NumberConstraint) rules[1]).getNumber(), grid);
                }
                else if(type.equals(ObjectiveCardType.COLOR)){
                    numberOfTimes = setOfColors(grid);
                }
                break;
    }
        return numberOfTimes*prize.value;

}

}
