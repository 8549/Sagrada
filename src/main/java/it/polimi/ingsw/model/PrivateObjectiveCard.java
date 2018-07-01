package it.polimi.ingsw.model;

import java.awt.*;
import java.io.Serializable;

public class PrivateObjectiveCard extends ObjCard implements Serializable {
    private int numberOfDice;

    public int sumColors(Cell[][] grid, SagradaColor color) {
        int sum = 0;
        int dice = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                if (!grid[i][j].isEmpty()) {
                    if (grid[i][j].getDie().getColor() == color)
                        sum = sum + grid[i][j].getDie().getNumber();
                        dice++;
                }
            }
        }
        numberOfDice = dice;
        return sum;
    }

    @Override
    public int checkObjective(Cell[][] grid) {
        if (this.type.equals(ObjectiveCardType.COLOR)) {
        return sumColors(grid, ((ColorConstraint) rules[0]).getColor());
        }
        return 0;
    }
   }

