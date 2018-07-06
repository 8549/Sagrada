package it.polimi.ingsw.model;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;

public class PlayerWindow implements Serializable {

    private WindowPattern windowPattern;
    //if the array is empty the value of the cell is a Die with number equal to 0
    private Cell[][] diceGrid;
    private boolean oneDie;

    public PlayerWindow() {
        diceGrid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
        oneDie= false;
        for (int i = 0; i < WindowPattern.ROWS; i++)
            for (int j = 0; j < WindowPattern.COLUMNS; j++)
                diceGrid[i][j] = new Cell();

    }

    public boolean isFirstPlacement(){
        for (int i = 0; i < WindowPattern.ROWS; i++){
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                if(!diceGrid[i][j].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    public void setOneDie(boolean one) {oneDie=one;}

    public boolean isOneDie() {
        return oneDie;
    }

    /**
     * Return a copy of the cell containing the die at the given coordinates
     *
     * @param row
     * @param column
     * @return Copy of the cell
     */
    public Cell getCellAt(int row, int column) {
        return diceGrid[row][column];
    }

    /**
     * Add die in the cell at the given coordinates
     *
     * @param die
     * @param row
     * @param column
     * @return True if the die was added, false if the cell wasn't empty
     */
    public boolean addDie(Die die, int row, int column) {
        if (diceGrid[row][column].isEmpty()) {
            return diceGrid[row][column].setDie(die);
        } else {
            return false;
        }

    }

    /**
     * Move die from the old coordinate to the new coordinate
     *
     * @param oldRow
     * @param oldColumn
     * @param newRow
     * @param newColumn
     * @return True if the die was moved, otherwise false
     */
    public boolean moveDie(int oldRow, int oldColumn, int newRow, int newColumn) {
        if (diceGrid[newRow][newColumn].setDie(diceGrid[oldRow][oldColumn].getDie())) {
            return diceGrid[oldRow][oldColumn].removeDie();
        } else {
            return false;
        }
    }


    public WindowPattern getWindowPattern() {
        return windowPattern;
    }


    public int dieCount() {
        return (int) Arrays.stream(diceGrid)
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isEmpty())
                .count();
    }


    public int emptyCount() {
        return (int) Arrays.stream(diceGrid)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.isEmpty())
                .count();

    }


    public boolean setWindowPattern(WindowPattern windowPattern){
        if(windowPattern!=null) {
            this.windowPattern = windowPattern;
            return true;
        }else{
            return false;
        }
    }

    public Cell[][] getDiceGrid() {
        return diceGrid;
    }
}
