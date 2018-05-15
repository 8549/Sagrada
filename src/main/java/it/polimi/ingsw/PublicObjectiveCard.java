package it.polimi.ingsw;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class PublicObjectiveCard extends ObjCard {
    private int numberOfTimes;
    private int points;

    public int setOfShades(int num1, int num2, Cell[][] grid) {
        int numbers[] = new int[6];
        int repetitions;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }

        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                if (!grid[i][j].isEmpty()) {
                    numbers[grid[i][j].getDie().getNumber() - 1]++;
                }
            }
        }
        if (num1 == 0 && num2 == 0) {
            repetitions = numbers[0];
            for (int number : numbers) {
                if (number < repetitions) {
                    repetitions = number;
                }
            }
            return repetitions;
        } else {
            return (numbers[num1 - 1] > numbers[num2 - 1]) ? numbers[num2 - 1] : numbers[num1 - 1];
        }

    }

    public int setOfColors(Cell[][] grid) {
        Map<SagradaColor, Integer> colorMap = new HashMap<>();
        for (SagradaColor color : SagradaColor.values()) {
            colorMap.put(color, 0);
        }
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                if (!grid[i][j].isEmpty()) {
                    SagradaColor key = grid[i][j].getDie().getColor();
                    int oldValue = colorMap.get(key);
                    colorMap.replace(key, ++oldValue);
                }
            }
        }
        return colorMap.values()
                .stream()
                .mapToInt(v -> v)
                .min()
                .orElse(0);

    }

    public int rowNumberVariety(Cell[][] grid) {
        int repetitions = 0;
        for (Cell[] row : grid) {
            if (!hasEmptyCells(row)) {
                if (WindowPattern.COLUMNS == Arrays.stream(row)
                        .mapToInt(v -> v.getDie().getNumber())
                        .distinct()
                        .count()) {
                    repetitions++;
                }
            }
        }
        return repetitions;
    }

    private boolean hasEmptyCells(Cell[] row) {
        for (Cell c : row) {
            if (c.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int columnNumberVariety(Cell[][] grid) {
        int repetitions = 0;

        for (int i = 0; i < WindowPattern.COLUMNS; i++) {
            Cell[] col = new Cell[WindowPattern.ROWS];
            for (int j = 0; j < WindowPattern.ROWS; j++) {
                col[j] = grid[j][i];
            }
            if (!hasEmptyCells(col)) {
                if (WindowPattern.ROWS == Arrays.stream(col)
                        .distinct()
                        .count()) {
                    repetitions++;
                }
            }
        }
        return repetitions;
    }


    public int diagonalsVariety(Cell[][] grid) {
        int repetitions = 0;
        boolean diagonals[][] = new boolean[WindowPattern.ROWS][WindowPattern.COLUMNS];
        for (boolean[] row : diagonals) {
            for (boolean element : row) {
                element = false;
            }
        }
        for (int i = 0; i < (WindowPattern.ROWS - 1); i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                try {
                    Cell thisCell = grid[i][j];
                    Cell downLeft = grid[i + 1][j - 1];
                    Cell downRight = grid[i + 1][j + 1];
                    if (!thisCell.isEmpty()) {
                        if (!downLeft.isEmpty()) {
                            if (thisCell.getDie().getColor().equals(downLeft.getDie().getColor())) {
                                diagonals[i][j] = true;
                                diagonals[i + 1][j - 1] = true;
                            }
                        }
                        if (!downRight.isEmpty()) {
                            if (thisCell.getDie().getColor().equals(downRight.getDie().getColor())) {
                                diagonals[i][j] = true;
                                diagonals[i + 1][j + 1] = true;
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Trying to access diagonals out of bounds");
                }
            }
        }

        for (boolean[] row : diagonals) {
            for (boolean e : row) {
                if (e) {
                    repetitions++;
                }
            }
        }
        return repetitions;
    }

    //TODO
    public int checkObjective(Cell[][] grid) {
        // read from file
        return 0;

    }

    //TODO
    public int getPoints() {
        return 0;
    }
}
