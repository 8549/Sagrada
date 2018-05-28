package it.polimi.ingsw.model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NumberConstraint implements PatternConstraint {
    private int number;

    public NumberConstraint(int number) {
        this.number = number;
    }

    @Override
    public boolean checkConstraint(Die die) {
        return number == die.getNumber();
    }

    @Override
    public Node getAsGraphic(double size) {
        Image img = new Image("images/shades/" + number + ".png");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        return imageView;
    }

    public int getNumber() {
        return number;
    }
}