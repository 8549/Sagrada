package it.polimi.ingsw.model;

public class Turn {
    private Player player;
    private int number;
    private boolean diePlaced;
    private boolean toolCardUsed;


    public Turn(Player player, int number) {
        this.player = player;
        this.number = number;
        diePlaced = false;
        toolCardUsed = false;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumber() {
        return number;
    }

    public boolean isDiePlaced() {
        return diePlaced;
    }

    public boolean isToolCardUsed() {
        return toolCardUsed;
    }

    public boolean setDiePlaced() {
        return diePlaced = true;
    }

    public boolean setDieNotPlaced() {return  diePlaced=false;}

    public boolean setToolCardUsed() {
        return toolCardUsed = true;
    }

    public void modifyTurn() {
        number = 2;
    }
}
