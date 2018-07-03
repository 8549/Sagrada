package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTrackTest {


    @Test
    void testGetRoundCounter() {
        List<Die> dice = new ArrayList<>();
        Die die = new Die(SagradaColor.PURPLE);
        dice.add(die);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.addRound(dice);
        roundTrack.addRound(dice);
        assertEquals(2, roundTrack.getRoundCounter());

    }

    @Test
    void testAddRound() {
        List<Die> dice = new ArrayList<>();
        Die die = new Die(SagradaColor.PURPLE);
        dice.add(die);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.addRound(dice);
        assertEquals(die, roundTrack.getDieAt(0,0));
        Die die1= new Die(SagradaColor.GREEN);
        dice.add(die1);
        roundTrack.addRound(dice);
        assertEquals(die, roundTrack.getDieAt(1,0));
        assertEquals(die1, roundTrack.getDieAt(1,1));
    }


    @Test
    void testReplaceDie() {
        List<Die> dice1 = new ArrayList<>();
        Die die1 = new Die(SagradaColor.PURPLE);
        dice1.add(die1);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.addRound(dice1);
        Die die2 = new Die(SagradaColor.GREEN);
        List<Die> dice2 = new ArrayList<>();
        dice2.add(die2);
        roundTrack.addRound(dice2);
        Die die = new Die(SagradaColor.RED);
        roundTrack.replaceDie(die, 2, 0);
        assertEquals(die, roundTrack.getDieAt(1, 0));
    }

    @Test
    void testGetDiceNumberAtRound() {
        List<Die> dice1 = new ArrayList<>();
        Die die1 = new Die(SagradaColor.PURPLE);
        dice1.add(die1);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.addRound(dice1);
        assertEquals(1, roundTrack.getDiceNumberAtRound(0));
    }

    @Test
    void testGetDieAt() {
        List<Die> dice = new ArrayList<>();
        Die die = new Die(SagradaColor.PURPLE);
        dice.add(die);
        RoundTrack roundTrack = new RoundTrack();
        roundTrack.addRound(dice);
        assertEquals(die, roundTrack.getDieAt(0,0));
        Die die1= new Die(SagradaColor.GREEN);
        dice.add(die1);
        roundTrack.addRound(dice);
        assertEquals(die, roundTrack.getDieAt(1,0));
    }


}