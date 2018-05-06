package it.polimi.ingsw;

public interface PatternConstraint {
    boolean checkConstraint(Die die);

    @Override
    String toString();
}
