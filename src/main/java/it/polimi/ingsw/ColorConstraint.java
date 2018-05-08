package it.polimi.ingsw;

import java.util.Objects;

public class ColorConstraint implements PatternConstraint {
    private SagradaColor color;


    public ColorConstraint(SagradaColor color) {
        this.color = color;
    }


    public boolean checkConstraint(Die die) {
        return color.equals(die.getColor());
    }

    @Override
    public String toString() {
        return color.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorConstraint that = (ColorConstraint) o;
        return Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(color);
    }
}

