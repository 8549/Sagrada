package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testIsEmpty() {
        Cell cell = new Cell();
        assertTrue(cell.isEmpty());
        Die die = new Die(SagradaColor.BLUE);
        cell.setDie(die);
        assertFalse(cell.isEmpty());
    }


    @Test
    void testSetDie() {
        Die die = new Die(SagradaColor.BLUE);
        Cell cell = new Cell();

        if (cell.isEmpty()) {
            cell.setDie(die);
            assertEquals(die.getNumber(), cell.getDie().getNumber());
            assertEquals(die.getColor().toString(), cell.getDie().getColor().toString());
        } else {
            assertFalse(cell.removeDie());
        }


    }


    @Test
    void testGetDie() {
        Cell cell = new Cell();
        assertNull(cell.getDie());
        Die die = new Die(SagradaColor.RED);
        assertTrue(cell.setDie(die));
        assertNotNull(cell.getDie());
        assertEquals(die, cell.getDie());
    }

    @Test
    void testRemoveDie() {
        Cell cell1 = new Cell();
        Die die = new Die(SagradaColor.BLUE);
        assertFalse(cell1.removeDie());
        cell1.setDie(die);
        assertTrue(cell1.removeDie());
    }

}