package it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NumberConstraint implements PatternConstraint {
    private int number;

    public NumberConstraint(int number) {
        this.number = number;
    }

    /**
     * Check if the die abides by the color constraint
     * @param die
     * @param modifier
     * @return true if the number of the die is the same as the number of the constraint, false otherwise
     */
    @Override
    public boolean checkConstraint(Die die, CheckModifier modifier) {
        if (modifier.equals(CheckModifier.NONUMBER)){
        return true;}
        return number == die.getNumber();
    }

    /**
     * Get the appropriate graphic JavaFX Node for the constraint
     *
     * @param size
     * @return a JavaFX Node representing the constraint
     */
    @Override
    public Node getAsGraphic(double size) {
        Image img = new Image("images/shades/" + number + "@4x.png");
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setCache(true);
        return imageView;
    }

    @Override
    public JsonElement getAsJson() {
        return new JsonPrimitive(number);
    }

    /**
     * Gets a string representation of the constraint
     *
     * @return the representation of the number of the constraint
     */
    @Override
    public String toCLI() {
        return "" + number;
    }

    public int getNumber() {
        return number;
    }
}