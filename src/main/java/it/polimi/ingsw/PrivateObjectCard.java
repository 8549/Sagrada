package it.polimi.ingsw;

//TODO: IMPPLEMENT CLASS
public class PrivateObjectCard extends ObjCard {

    private int numberOfTimes;
    private int points;
    private Die die = new Die(SagradaColor.BLUE);

    public int checkObject(WindowPattern window, Cell[][] grid) {
        return 0;
    }

    public int getPoints() {
        return 0;
    }
}

