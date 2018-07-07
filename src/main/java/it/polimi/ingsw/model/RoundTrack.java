package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundTrack {
    private Map<Integer, List<Die>> dice;
    private int roundCounter;

    public RoundTrack() {
        roundCounter = 0;
        dice = new HashMap<>();
        for (int i=0; i<GameManager.ROUNDS; i++){
            dice.put(i, new ArrayList<>());
        }
    }

    public int getDiceNumberAtRound(int i) {
        return dice.get(i).size();
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public Die getDieAt(int roundCounter, int numberOfDie){
        return dice.get(roundCounter).get(numberOfDie);
    }

    public void addRound(List<Die> dice) {
        if (dice.size()==0){
            System.err.println("There are no dice to add to the round track");
            return;
        }
        for (int i=0; i<dice.size(); i++) {
            this.dice.get(roundCounter).add(dice.get(i));
        }
        roundCounter++;
    }

    public Die replaceDie(Die die, int turn, int numberOfDie) {
        if (turn <roundCounter) {
            Die oldDie = dice.get(turn).remove(numberOfDie);
            dice.get(turn).add(die);
            return oldDie;
        }
        return null;
    }

    public int getDiePosition(int round, Die die){
        for (int i=0; i<getDiceNumberAtRound(round); i++) {
            if(dice.get(round).get(i).getColor().equals(die.getColor()) && dice.get(round).get(i).getNumber()==die.getNumber()){
                return i;
            }
        }
        return -1;
    }
}
