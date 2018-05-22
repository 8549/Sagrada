package it.polimi.ingsw.model;

public class BlankConstraint implements PatternConstraint {

    public boolean checkConstraint(Die die) {
        return true;
    }

    @Override
    public String toString() {
        return "BLANK";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BlankConstraint);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
