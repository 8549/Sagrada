package it.polimi.ingsw.model;

import it.polimi.ingsw.model.DiceBag;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceBagTest {

    @Test
    void draftDie() {
        Die die2;
        DiceBag diceBag= new DiceBag();
        die2 = diceBag.draftDie();

        assertEquals(diceBag.getSize(), 89);

    }

    @Test
    void getSizeDice() {
    }

    @Test
    void addDie() {
        Die die = new Die(SagradaColor.BLUE);
        DiceBag diceBag= new DiceBag();
        assertFalse(diceBag.addDie(die));
        Die die1 = diceBag.draftDie();
        assertTrue(diceBag.addDie(die));
    }


}