package it.polimi.ingsw;

import javafx.scene.paint.Color;

public enum SagradaColor {
    BLUE("#2dbbc8", "\u001B[34m"),
    GREEN("#03ab6c", "\u001B[32m"),
    PURPLE("#a54198", "\u001B[35m"),
    RED("#dc2327", "\u001B[31m"),
    YELLOW("#f3de0c", "\u001B[33m");

    private Color color;
    private String escape;
    public static final String RESET = "\u001B[0m";

    SagradaColor(String hex, String escape) {
        this.color = Color.web(hex);
        this.escape = escape;
    }

    public Color getColor() {
        return color;
    }

    public String getEscape() {
        return escape;
    }

    public static void main(String[] args) {
        for (SagradaColor c : SagradaColor.values()) {
            System.out.println(c.getEscape() + c + SagradaColor.RESET);
        }
    }


}