package it.polimi.ingsw.model;

import javafx.scene.paint.Color;

public enum SagradaColor {
    BLUE("#2dbbc8", 34),
    GREEN("#03ab6c", 32),
    PURPLE("#a54198", 35),
    RED("#dc2327", 31),
    YELLOW("#f3de0c", 33);

    private Color color;
    private int escape;
    public static final String RESET = "\u001B[0m";

    SagradaColor(String hex, int escape) {
        this.color = Color.web(hex);
        this.escape = escape;
    }

    public Color getColor() {
        return color;
    }

    public int getEscape() {
        return escape;
    }

    public String escapeString() {
        return "\u001B[" + escape + "m";
    }

    public static void main(String[] args) {
        for (SagradaColor c : SagradaColor.values()) {
            System.out.println(c.escapeString() + c + SagradaColor.RESET);
        }
    }


}