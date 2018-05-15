package it.polimi.ingsw;

public class PrivateObjectiveCard extends ObjCard {
    private int points;


    public int sumColors(Cell[][] grid, SagradaColor color) {
        int sum = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                if (!grid[i][j].isEmpty()) {
                    if (grid[i][j].getDie().getColor() == color)
                        sum = sum + grid[i][j].getDie().getNumber();
                }
            }
        }
        return sum;
    }

    //TODO
    public int checkObjective(Cell[][] grid) {
        // read from file
        return 0;
    }


    public int getPoints() {
        return points;
    }
}

