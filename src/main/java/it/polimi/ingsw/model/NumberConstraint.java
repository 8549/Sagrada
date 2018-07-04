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

    @Override
    public boolean checkConstraint(Die die, CheckModifier modifier) {
        if (modifier.equals(CheckModifier.NONUMBER)){
        return true;}
        return number == die.getNumber();
    }

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

    @Override
    public String toCLI() {
        return "" + number;
    }

    public int getNumber() {
        return number;
    }
}