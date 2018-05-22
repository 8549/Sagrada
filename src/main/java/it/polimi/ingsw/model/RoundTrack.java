package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;

public class RoundTrack {
    private static RoundTrack instance;
    private Die[] dice;
    private int roundCounter;

    private RoundTrack() {
        roundCounter = 0;
        dice = new Die[GameManager.ROUNDS];
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
        this.dice[roundCounter] = die;
        roundCounter++;
    }

    public SagradaColor getColorOfDieAtRound(int turn) {
        return dice[turn - 1].getColor();
    }

    public Die replaceDie(Die die, int turn) {
        if (turn <= roundCounter) {
            Die oldDie = dice[turn - 1];
            dice[turn - 1] = die;
            return oldDie;
        }
        return null;
    }
}
