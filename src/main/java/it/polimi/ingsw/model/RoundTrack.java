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

    /**
     * Adds the given dice to the pool of dice of the current round
     * set the pointer to the next round
     * @param dice to add to the round track
     */
    public void addRound(List<Die> dice) {
        if (dice.size()==0){
            System.err.println("There are no dice to add to the round track");
            return;
        }
        this.dice.put(roundCounter, dice);
        /*for (int i=0; i<dice.size(); i++) {
            //this.dice.get(roundCounter).add(dice.get(i));
        }*/
        roundCounter++;
    }

    /**
     * Replace the die of the given round at the given position with the given die
     * @param die to put in the round track
     * @param turn : the number of the round where the die that needs to be replaced is
     * @param numberOfDie : the position in the round where the die that needs to be replaced is
     * @return the exchanged die of the given position
     */
    public Die replaceDie(Die die, int turn, int numberOfDie) {
        if (turn <roundCounter) {
            Die oldDie = dice.get(turn).remove(numberOfDie);
            dice.get(turn).add(die);
            return oldDie;
        }
        return null;
    }

    /**
     * Given a die and his round it gives the position
     * @param round : the number of the round where the searched die is
     * @param die : searched d
     * @return the position in the round where the die is
     */
    public int getDiePosition(int round, Die die){
        for (int i=0; i<getDiceNumberAtRound(round); i++) {
            if(dice.get(round).get(i).getColor().equals(die.getColor()) && dice.get(round).get(i).getNumber()==die.getNumber()){
                return i;
            }
        }
        return -1;
    }
}
