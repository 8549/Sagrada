package it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlankConstraint implements PatternConstraint {

    @Override
    public boolean checkConstraint(Die die, CheckModifier modifier) {
        return true;
    }

    @Override
    public Node getAsGraphic(double size) {
        Rectangle rect = new Rectangle();
        rect.setWidth(size);
        rect.setHeight(size);
        rect.setFill(Color.TRANSPARENT);
        return rect;
    }

    @Override
    public JsonElement getAsJson() {
        return JsonNull.INSTANCE;
    }

    @Override
    public String toCLI() {
        return " ";
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
