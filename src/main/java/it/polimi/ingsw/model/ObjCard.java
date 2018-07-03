package it.polimi.ingsw.model;

import java.io.Serializable;

public abstract class ObjCard implements Card, Serializable{
    String name;
    ObjectiveCardType type;
    ObjectiveCardWhere where;
    PatternConstraint[] rules;
    Prize prize;

    public abstract int checkObjective(Cell[][] grid);

    public String getName(){
        return this.name;
    }
}
