package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class ObjCard implements Card, Serializable{
    String name;
    ObjectiveCardType type;
    ObjectiveCardWhere where;
    PatternConstraint[] rules;
    Prize prize;
    int numberOfDice;
    int numberOfTimes;

    /**
     * Counts points earned according to the public or private objective card selected
     * @param grid
     * @return points earned with public or private objective cards
     */
    public abstract int checkObjective(Cell[][] grid);

    public String getName(){
        return this.name;
    }

    /**
     * Counts how many repetitions there are for every number
     * @param num1 0 if it needs to be checked every die's repetitions, a number between 1 and 6 otherwise
     * @param num2 0 if it needs to be checked every die's repetitions, a number between 1 and 6 otherwise
     * @param grid
     * @return the minimum number of repetitions between every number if num1, num2=0, otherwise between num1, num2
     */
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

    /**
     *Counts the number of repetitions for every shade and returns the minor number of occurrence
     * @param grid
     * @return number of repetitions of the color with the minor number of occurrence
     */
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

    /**
     * Controls if a given row is empty
     * @param row
     * @return true if it's empty, false otherwise
     */
    private boolean hasEmptyCells(Cell[] row) {
        for (Cell c : row) {
            if (c.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts how many rows have all dice with different number
     * @param grid
     * @return the number of rows that have all dice with different number
     */
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

    /**
     * Counts how many columns have all dice with different number
     * @param grid
     * @return the number of columns that have all dice with different number
     */
    public int columnNumberVariety(Cell[][] grid) {
        int repetitions = 0;
        Cell[] col;
        for (int i = 0; i < WindowPattern.COLUMNS; i++) {
            col = new Cell[WindowPattern.ROWS];
            for (int j = 0; j < WindowPattern.ROWS; j++) {
                col[j] = grid[j][i];
            }
            if (!hasEmptyCells(col)) {
                if (WindowPattern.ROWS == Arrays.stream(col)
                        .mapToInt(v -> v.getDie().getNumber())
                        .distinct()
                        .count()) {
                    repetitions++;
                }
            }
        }
        return repetitions;
    }

    /**
     * Counts how many rows have all dice with different color
     * @param grid
     * @return the number of rows that have all dice with different color
     */
    public int rowColorVariety(Cell[][] grid) {
        int repetitions = 0;
        for (Cell[] row : grid) {
            if (!hasEmptyCells(row)) {
                if (WindowPattern.COLUMNS == Arrays.stream(row)
                        .mapToInt(v -> v.getDie().getColor().getEscape())
                        .distinct()
                        .count()) {
                    repetitions++;
                }
            }
        }
        return repetitions;
    }

    /**
      * Counts how many columns have all dice with different color
     * @param grid
     * @return the number of columns that have all dice with different color
     */
    public int columnColorVariety(Cell[][] grid) {
        int repetitions = 0;
        Cell[] col;
        for (int i = 0; i < WindowPattern.COLUMNS; i++) {
            col = new Cell[WindowPattern.ROWS];
            for (int j = 0; j < WindowPattern.ROWS; j++) {
                col[j] = grid[j][i];
            }
            if (!hasEmptyCells(col)) {
                if (WindowPattern.ROWS == Arrays.stream(col)
                        .mapToInt(v -> v.getDie().getColor().getEscape())
                        .distinct()
                        .count()) {
                    repetitions++;
                }
            }
        }
        return repetitions;
    }

    /**
     * Counts how many dice are in diagonals
     * @param grid
     * @return the number of dice that are in diagonals
     */
    public int diagonalsVariety(Cell[][] grid) {
        int repetitions = 0;
        boolean diagonals[][] = new boolean[WindowPattern.ROWS][WindowPattern.COLUMNS];
        for (boolean[] row : diagonals) {
            for (boolean element : row) {
                element = false;
            }
        }
        for (int i = 0; i < WindowPattern.ROWS - 1; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Cell thisCell = grid[i][j];
                int nextRow = i + 1;
                int downLeft = j - 1;
                int downRight = j + 1;
                if (!thisCell.isEmpty()) {
                    try {
                        if (!grid[nextRow][downLeft].isEmpty()) {
                            if (thisCell.getDie().getColor().equals(grid[nextRow][downLeft].getDie().getColor())) {
                                diagonals[i][j] = true;
                                diagonals[nextRow][downLeft] = true;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(String.format("%s (grid[%d][%d])", e.toString(), nextRow, downLeft));
                    }
                    try {
                        if (!grid[nextRow][downRight].isEmpty()) {
                            if (thisCell.getDie().getColor().equals(grid[nextRow][downRight].getDie().getColor())) {
                                diagonals[i][j] = true;
                                diagonals[nextRow][downRight] = true;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(String.format("%s (grid[%d][%d])", e.toString(), nextRow, downRight));
                    }
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

    /**
     *  Adds the value of every die in the grid with a given color
     * @param grid
     * @param color
     * @return the sum of the values of every die in the grid with a given color
     */
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

}
