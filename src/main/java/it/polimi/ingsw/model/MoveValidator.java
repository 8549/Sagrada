package it.polimi.ingsw.model;

import java.util.List;

public class MoveValidator {
    private Turn turn;
    private List<Die> draftPool;
    private boolean colorConstraint;
    private boolean numberConstraint;
    private boolean adjacencyConstraint;

    // controlla giocatore, turno, dado da draft pool, constraint (ha 3 valori booleani) e coordinate (che siano dentro le 4x5)

    public MoveValidator(Turn turn, List<Die> draftPool, boolean number, boolean color, boolean adjacency) {
        this.draftPool = draftPool;
        this.turn = turn;
        colorConstraint = color;
        numberConstraint = number;
        adjacencyConstraint = adjacency;
    }

    public boolean validateMove(Die die, int row, int column, Player player) {
        if (row < 0 || row > WindowPattern.ROWS) {
            return false;
        }
        if (column < 0 || column > WindowPattern.COLUMNS) {
            return false;
        }
        if (!draftPool.contains(die)) {
            return false;
        }

        if (!turn.getPlayer().equals(player)) {
            return false;
        }


        if (turn.getPlayer().getPlayerWindow().isFirstPlacement()) {
            if (!AdjacencyConstraint.checkAdjacencyFirstDie(row, column)) {
                return false;
            }
        } else if (adjacencyConstraint) {
            if (!AdjacencyConstraint.checkCellAdjacency(turn.getPlayer().getPlayerWindow().getDiceGrid(), row, column)) {
                return false;
            }
        }

        if (!AdjacencyConstraint.checkAdjacency(turn.getPlayer().getPlayerWindow().getDiceGrid(), row, column, die)) {
            return false;
        }

        if (!AdjacencyConstraint.checkEmptyCell(turn.getPlayer().getPlayerWindow().getDiceGrid(), row, column)){
            return false;
        }

        if (colorConstraint && numberConstraint) {
            if (!turn.getPlayer().getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NORMAL)) {
                return false;
            }
        }


        if (!colorConstraint && numberConstraint) {
            if (!turn.getPlayer().getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NOCOLOR)) {
                return false;
            }
        }


        if (colorConstraint && !numberConstraint) {
            if (!turn.getPlayer().getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NONUMBER)) {
                return false;
            }
        }

        return true;
    }
}
