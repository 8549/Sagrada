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
        DiceBag istance = DiceBag.getInstance();
        die2 = DiceBag.draftDie();

        assertEquals(DiceBag.getSize(), 89);

    }

    @Test
    void getSizeDice() {
    }

    @Test
    void addDie() {
        Die die = new Die(SagradaColor.BLUE);
        DiceBag istance = DiceBag.getInstance();
        assertFalse(DiceBag.addDie(die));
        Die die1 = DiceBag.draftDie();
        assertTrue(DiceBag.addDie(die));
    }


}