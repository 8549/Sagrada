package it.polimi.ingsw.model;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class ColorConstraint implements PatternConstraint {
    private SagradaColor color;

    public ColorConstraint(SagradaColor color) {
        this.color = color;
    }

    @Override
    public boolean checkConstraint(Die die) {
        return color.equals(die.getColor());
    }

    @Override
    public Node getAsGraphic(double size) {
        Rectangle rect = new Rectangle();
        rect.setWidth(size);
        rect.setHeight(size);
        rect.setFill(color.getColor());
        return rect;
    }

    @Override
    public String toCLI() {
        int unicodeNumber = 9632;
        return color.escapeString() + (char) unicodeNumber + SagradaColor.RESET;
    }

    public SagradaColor getColor() {
        return color;
    }
}

