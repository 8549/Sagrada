package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundTrack {                             
    private static RoundTrack instance;
    private Map<Integer, List<Die>> dice;
    private int roundCounter;

    private RoundTrack() {
        roundCounter = 0;
        dice = new HashMap<>();
        for (int i=0; i<GameManager.ROUNDS; i++){
            dice.put(i, new ArrayList<>());
        }
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

    public Die getDieAt(int roundCounter, int numberOfDie){
        return dice.get(roundCounter).get(numberOfDie);
    }

    public void addRound(List<Die> dice) {
        for (int i=0; i<dice.size(); i++) {
            this.dice.get(roundCounter).add(dice.get(i));
        }
        roundCounter++;
    }

    public SagradaColor getColorOfDieAtRound(int turn, int numberOfDie) {
        return dice.get(turn - 1).get(numberOfDie).getColor();
    }

    public Die replaceDie(Die die, int turn, int numberOfDie) {
        if (turn <= roundCounter) {
            Die oldDie = dice.get(turn - 1).remove(numberOfDie);
            dice.get(turn - 1).add(die);
            return oldDie;
        }
        return null;
    }
}
