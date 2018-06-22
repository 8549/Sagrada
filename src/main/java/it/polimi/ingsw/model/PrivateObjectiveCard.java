package it.polimi.ingsw.model;

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

    //TODO
    public int checkObjective(Cell[][] grid) {
        return 0;
    }
}

