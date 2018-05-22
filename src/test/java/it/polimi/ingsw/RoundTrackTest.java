package it.polimi.ingsw;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.RoundTrack;
import it.polimi.ingsw.model.SagradaColor;
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