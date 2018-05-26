package it.polimi.ingsw.model;

public abstract class ObjCard {
    private String name;
    private int points;
    private ObjectiveCardType type;
    private ObjectiveCardWhere where;
    private PatternConstraint[] rules;


    public abstract int checkObjective(Cell[][] grid);

    enum ObjectiveCardType {
        COLOR,
        NUMBER
    }

    enum ObjectiveCardWhere {
        COLUMN,
        ROW,
        DIAGONALS,
        EVERYWHERE
    }

    enum PrizeType {
        FIXED,
        SUM,
        COUNT
    }

    class Prize {
        PrizeType type;
        int value;

    }
}
