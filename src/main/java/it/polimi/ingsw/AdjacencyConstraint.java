package it.polimi.ingsw;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.WindowPattern;

public class AdjacencyConstraint {

    /**
     * Check if it's possible to place the first die at the given coordinates, they must be on the edge of the grid
     *
     * @param row
     * @param column
     * @return true if the coordinates are on the edge of the grid, false otherwise
     */
    public boolean checkAdjacencyFirstDie(int row, int column) {
        if (row == WindowPattern.ROWS - 1 || row == 0) {
            return true;
        }
        if (column == WindowPattern.COLUMNS - 1 || column == 0) {
            return true;
        }
        return false;
    }

    /**
     * Controls if in the adjacent cells there are dice with the same number or color
     *
     * @param grid
     * @param row
     * @param column
     * @return true if there isn't a dice with the same number and color nearby, false otherwise
     */
    public boolean checkAdjacency(Cell grid[][], int row, int column) {
        try {
            if (!controlCell(grid, row - 1, column, grid[row][column].getDie())) {
                return false;
            }
            if (!controlCell(grid, row, column - 1, grid[row][column].getDie())) {
                return false;
            }
            if (!controlCell(grid, row, column + 1, grid[row][column].getDie())) {
                return false;
            }
            if (!controlCell(grid, row + 1, column, grid[row][column].getDie())) {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format("%s (grid[%d][%d])"));
        }

        return true;
    }

    /**
     * Controls if the number and the color of the die at the given coordinates are different from the one of the given die
     *
     * @param grid
     * @param row
     * @param column
     * @param die
     * @return true if it's different, false otherwise
     */
    public boolean controlCell(Cell grid[][], int row, int column, Die die) {
        try {
            if (!grid[row][column].isEmpty()) {
                if (grid[row][column].getDie().getNumber() == die.getNumber()) {
                    return false;
                }
                if (grid[row][column].getDie().getColor() == die.getColor()) {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Check if there is a die in diagonal or adjacent to the given position
     *
     * @param grid
     * @param row
     * @param column
     * @return true when is found a die nearby, false if there isn't any
     */
    public boolean checkCellAdjacency(Cell grid[][], int row, int column) {
        try {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (!grid[i][j].isEmpty()) {
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Controls if the cell at the given coordinates is empty
     *
     * @param grid
     * @param row
     * @param column
     * @return true if it's empty, false otherwise
     */
    public boolean checkEmptyCell(Cell grid[][], int row, int column) {
        if (grid[row][column].isEmpty()) {
            return true;
        }
        return false;
    }

}
