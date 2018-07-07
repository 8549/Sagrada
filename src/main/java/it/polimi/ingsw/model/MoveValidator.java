package it.polimi.ingsw.model;

import java.util.List;

public class MoveValidator {
    private Turn turn;
    private List<Die> draftPool;
    private boolean colorConstraint;
    private boolean numberConstraint;
    private boolean adjacencyConstraint;


    public MoveValidator(Turn turn, List<Die> draftPool, boolean number, boolean color, boolean adjacency) {
        this.draftPool = draftPool;
        this.turn = turn;
        colorConstraint = color;
        numberConstraint = number;
        adjacencyConstraint = adjacency;
    }

    /**
     * Check if the move is valid verifying:
     * - the coordinates are within the limits
     * - the player of the turn is the same as the one is making the move
     * - if the die is placed on the edge of the grid if it's the first move or there is only one die on the grid (for tool card)
     *   or if adjacencyConstraint is true if the die is nearby  another die
     * - there are no adjacent die with the same number or color
     * - if all constraints rules are met if colorConstraint and numberConstraint are true
     * - if all constraints rules are met if colorConstraint is false and numberConstraint is true
     * - if all constraints rules are met if colorConstraint is true and numberConstraint is false
     *
     * @param die
     * @param row
     * @param column
     * @param player
     * @return true if the move is valid, false otherwise
     */
    public boolean validateMove(Die die, int row, int column, Player player) {
        if (row < 0 || row > WindowPattern.ROWS) {
            return false;
        }
        if (column < 0 || column > WindowPattern.COLUMNS) {
            return false;
        }

        if (!turn.getPlayer().getName().equals(player.getName())) {
            return false;
        }


        if (turn.getPlayer().getPlayerWindow().isFirstPlacement() || turn.getPlayer().getPlayerWindow().isOneDie()) {
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

        if (!AdjacencyConstraint.checkEmptyCell(turn.getPlayer().getPlayerWindow().getDiceGrid(), row, column)) {
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
            return turn.getPlayer().getPlayerWindow().getWindowPattern().getConstraint(row, column).checkConstraint(die, CheckModifier.NONUMBER);
        }

        return true;
    }
}
