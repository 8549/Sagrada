package it.polimi.ingsw;

public class NumberConstraint implements PatternConstraint {
    private int number;

    public NumberConstraint(int number) {
        this.number = number;
    }

    @Override
    public boolean checkConstraint(Die die) {
        return number == die.getNumber();
    }

    public int getNumber() {
        return number;
    }
}