package it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class ColorConstraint implements PatternConstraint {
    private SagradaColor color;

    public ColorConstraint(SagradaColor color) {
        this.color = color;
    }

    @Override
    public boolean checkConstraint(Die die, CheckModifier modifier) {
        if (modifier.equals(CheckModifier.NOCOLOR)) {
            return true;
        }
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
    public JsonElement getAsJson() {
        return new JsonPrimitive(color.name().toLowerCase());
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

