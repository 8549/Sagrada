package it.polimi.ingsw.model;

import java.util.List;

public class MoveValidator {
    private int row;
    private int column;
    private Player player;
    private List<Die> draftPool;
    private boolean colorConstraint;
    private boolean numberConstraint;
    private boolean adjacencyConstraint;

    // controlla giocatore, turno, dado da draft pool, constraint (ha 3 valori booleani) e coordinate (che siano dentro le 4x5)

    public MoveValidator(int row, int column, Player player, List<Die> draftPool, boolean number, boolean color, boolean adjacency) {
        this.row = row;
        this.column = column;
        this.player = player;
        this.draftPool = draftPool;
        colorConstraint = color;
        numberConstraint = number;
        adjacencyConstraint = adjacency;
    }

    public boolean validateMove(Die die, Player player) {
        if (row < 0 || row > WindowPattern.ROWS) {
            return false;
        }
        if (column < 0 || column > WindowPattern.COLUMNS) {
            return false;
        }
        if (!draftPool.contains(die)) {
            return false;
        }

        if (!this.player.equals(player)) {
            return false;
        }
        if (!AdjacencyConstraint.checkAdjacency(this.player.getPlayerWindow().getDiceGrid(), row, column, die)) {
            return false;
        }

        if (adjacencyConstraint) {
            if (!AdjacencyConstraint.checkCellAdjacency(this.player.getPlayerWindow().getDiceGrid(), row, column)) {
                return false;
            }
        }

        if (colorConstraint && numberConstraint) {
            if (!this.player.getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NORMAL)) {
                return false;
            }
        }


        if (!colorConstraint && numberConstraint) {
            if (!this.player.getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NOCOLOR)) {
                return false;
            }
        }


        if (colorConstraint && !numberConstraint) {
            if (!this.player.getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NONUMBER)) {
                return false;
            }
        }

        return true;
    }
}
