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

    class Prize implements Serializable {
        PrizeType type;
        int value;

    }

    public String getName(){
        return this.name;
    }
}
