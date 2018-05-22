package it.polimi.ingsw;

import it.polimi.ingsw.model.DiceBag;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceBagTest {

    @Test
    void draftDie() {
        DiceBag dicebag = new DiceBag();
        Die die2;
        die2 = dicebag.draftDie();

        assertEquals(dicebag.getSizeDice(), 89);

    }


    @Test
    void getSizeDice() {
    }

    @Test
    void addDie() {
        DiceBag diceBag = new DiceBag();
        Die die = new Die(SagradaColor.BLUE);
        assertFalse(diceBag.addDie(die));
        Die die1 = diceBag.draftDie();
        assertTrue(diceBag.addDie(die));
    }
}
