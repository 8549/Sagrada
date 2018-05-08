package it.polimi.ingsw;

import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Random;

public class Die {
    private int number;
    private Color color;
    public static final int MAX = 6;
    public static final int MIN = 1;

    public Die(Color color) {
        this.color = color;
        roll();
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }


    public void roll() {
        Random random = new Random();
        this.number = random.nextInt(MAX + 1 - MIN) + MIN;
    }

    public void setNumber(int value) {
        this.number = value;
    }

    /**
     * Flip the face of the die
     */
    public void flip() {
        int oldNumber = this.number;
        this.number = 7 - oldNumber;
    }


    /**
     * Increase value of the number of the die by one
     */
    public void increase() {
        if (this.number != MAX) {
            this.number++;
        }
    }

    /**
     * Decrease value of the number of the die by one
     */
    public void decrease() {
        if (this.number != MIN) {
            this.number--;
        }
    }

    @Override
    public String toString() {
        return "Die{" +
                "number=" + number +
                ", color=" + color.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Die die = (Die) o;
        return number == die.number &&
                Objects.equals(color, die.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(number, color);
    }

    public static boolean checkValue(int n) {
        return (n >= MIN && n <= MAX);
    }

    public boolean checkValue() {
        return checkValue(number);
    }
}