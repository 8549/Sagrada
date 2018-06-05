package it.polimi.ingsw.model;

//TODO 
public class ToolCard {
    private int tokens;
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public int getCost() {
        if (used) {
            return 2;
        } else {
            return 1;
        }
    }

    public void addTokens() {
        tokens = tokens + getCost();
    }

    public void useTools() {

        used = true;
        addTokens();
    }

    public int getTokens() {
        return tokens;
    }
}
