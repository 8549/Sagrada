package it.polimi.ingsw;

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
}
