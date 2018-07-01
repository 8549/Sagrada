package it.polimi.ingsw.model;

import java.io.Serializable;

public abstract class ObjCard implements Card, Serializable{
    String name;
    private int points;
    ObjectiveCardType type;
    ObjectiveCardWhere where;
    PatternConstraint[] rules;
    Prize prize;

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
