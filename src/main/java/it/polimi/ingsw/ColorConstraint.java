package it.polimi.ingsw;

import javafx.scene.paint.Color;

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
        return "R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue();
    }
}

