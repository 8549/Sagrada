package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundTrackTest {

    @Test
    void getInstance() {
    }

    @Test
    void getRoundCounter() {
    }


    @Test
    void addRound() {
        Die die = new Die(SagradaColor.BLUE);
        RoundTrack instance = RoundTrack.getInstance();
        instance.addRound(die);
        assertEquals(instance.getRoundCounter(), 1);
        assertEquals(instance.getDieAtRound(instance.getRoundCounter()),die);
    }

    @Test
    void getDieAtRound() {
    }
}