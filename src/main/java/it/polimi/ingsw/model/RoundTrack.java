package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;

public class RoundTrack {
    private static RoundTrack instance;
    private Die[] die;
    private int roundCounter;

    private RoundTrack() {
        roundCounter = 0;
        die = new Die[GameManager.ROUNDS];
    }

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
        return die[turn - 1];
    }


}
