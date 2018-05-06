package it.polimi.ingsw;

import java.util.Objects;

public class NumberConstraint implements PatternConstraint {
    private int number;

    public NumberConstraint(int number) {
        this.number = number;
    }

    public boolean checkConstraint(Die die) {
        return number == die.getNumber();
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberConstraint that = (NumberConstraint) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {

        return Objects.hash(number);
    }
}