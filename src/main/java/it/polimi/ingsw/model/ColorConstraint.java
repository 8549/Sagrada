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

    /**
     * Check if the die abides by the color constraint
     * @param die
     * @param modifier
     * @return true if the color of the die is the same as the color of the constraint, false otherwise
     */
    @Override
    public boolean checkConstraint(Die die, CheckModifier modifier) {
        if (modifier.equals(CheckModifier.NOCOLOR)) {
            return true;
        }
            return color.equals(die.getColor());
    }

    /**
     * Get the appropriate graphic JavaFX Node for the constraint
     *
     * @param size
     * @return a JavaFX Node representing the constraint
     */
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

    /**
     * Gets a string representation of the constraint
     *
     * @return the representation of the color of the constraint
     */
    @Override
    public String toCLI() {
        int unicodeNumber = 9632;
        return color.escapeString() + (char) unicodeNumber + SagradaColor.RESET;
    }

    public SagradaColor getColor() {
        return color;
    }
}

