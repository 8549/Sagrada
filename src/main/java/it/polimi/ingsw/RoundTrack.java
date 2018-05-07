package it.polimi.ingsw;

public class RoundTrack {
    private static RoundTrack instance;
    private Die[] die;
    private int roundCounter = 0;

    public static RoundTrack getInstance() {
        if (instance == null) {
            instance = new RoundTrack();
        }
        return instance;

    }

    public int getRoundCounter() {
        return roundCounter;
    }


    public void addRound(Die die) {
        this.die[roundCounter] = die;
        roundCounter++;
    }

    public Die getDieAtRound(int turn) {
        return die[turn];
    }


}
