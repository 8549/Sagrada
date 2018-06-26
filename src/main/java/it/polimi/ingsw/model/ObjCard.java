package it.polimi.ingsw.model;

import java.io.Serializable;

public abstract class ObjCard implements Card, Serializable{
    private String name;
    private int points;
    private ObjectiveCardType type;
    private ObjectiveCardWhere where;
    private PatternConstraint[] rules;
    private Prize prize;

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

    class Prize implements Serializable {
        PrizeType type;
        int value;

    }

    public String getName(){
        return this.name;
    }
}
