package it.polimi.ingsw;

import javafx.scene.paint.Color;

import java.util.Objects;

public class ColorConstraint implements PatternConstraint {
    private Color color;


    public ColorConstraint(Color color) {
        this.color = color;
    }


    public boolean checkConstraint(Die die) {
        return color.equals(die.getColor());
    }

    @Override
    public String toString() {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
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

