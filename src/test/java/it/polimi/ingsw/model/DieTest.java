package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.SagradaColor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    @Test
    void testRoll() {
        double[] histogram = {0, 0, 0, 0, 0, 0};
        Die die = new Die(SagradaColor.GREEN);
        int n;
        int tries = 1000000;
        double epsilon = 0.001;
        for (int i = 0; i < tries; i++) {
            n = die.getNumber();
            histogram[n - 1]++;
            assertTrue(die.checkValue());
            die.roll();
        }
        Arrays.stream(histogram)
                .map(h -> h / tries)
                .forEach(h -> assertEquals(h, Die.MIN / (double) Die.MAX, epsilon));
    }

    @Test
    void testSetNumber() {
        Die die = new Die(SagradaColor.BLUE);
        int number = 2;
        die.setNumber(number);
        assertEquals(die.getNumber(), number);

    }

    @Test
    void testFlip() {
        Die die1 = new Die(SagradaColor.BLUE);
        die1.setNumber(1);
        Die die2 = new Die(SagradaColor.BLUE);
        die2.setNumber(2);
        Die die3 = new Die(SagradaColor.BLUE);
        die3.setNumber(3);
        Die die4 = new Die(SagradaColor.BLUE);
        die4.setNumber(4);
        Die die5 = new Die(SagradaColor.BLUE);
        die5.setNumber(5);
        Die die6 = new Die(SagradaColor.BLUE);
        die6.setNumber(6);
        die1.flip();
        die2.flip();
        die3.flip();
        die4.flip();
        die5.flip();
        die6.flip();
        assertEquals(die1.getNumber(), 7 - 1);
        assertEquals(die2.getNumber(), 7 - 2);
        assertEquals(die3.getNumber(), 7 - 3);
        assertEquals(die4.getNumber(), 7 - 4);
        assertEquals(die5.getNumber(), 7 - 5);
        assertEquals(die6.getNumber(), 7 - 6);

    }

    @Test
    void testIncrease() {
        Die die1 = new Die(SagradaColor.BLUE);
        die1.setNumber(1);
        Die die2 = new Die(SagradaColor.BLUE);
        die2.setNumber(2);
        Die die3 = new Die(SagradaColor.BLUE);
        die3.setNumber(3);
        Die die4 = new Die(SagradaColor.BLUE);
        die4.setNumber(4);
        Die die5 = new Die(SagradaColor.BLUE);
        die5.setNumber(5);
        Die die6 = new Die(SagradaColor.BLUE);
        die6.setNumber(6);
        die1.increase();
        die2.increase();
        die3.increase();
        die4.increase();
        die5.increase();
        die6.increase();
        assertEquals(die1.getNumber(), 1 + 1);
        assertEquals(die2.getNumber(), 2 + 1);
        assertEquals(die3.getNumber(), 3 + 1);
        assertEquals(die4.getNumber(), 4 + 1);
        assertEquals(die5.getNumber(), 5 + 1);
        assertEquals(die6.getNumber(), 6);
    }


    @Test
    void testDecrease() {
        Die die1 = new Die(SagradaColor.BLUE);
        die1.setNumber(1);
        Die die2 = new Die(SagradaColor.BLUE);
        die2.setNumber(2);
        Die die3 = new Die(SagradaColor.BLUE);
        die3.setNumber(3);
        Die die4 = new Die(SagradaColor.BLUE);
        die4.setNumber(4);
        Die die5 = new Die(SagradaColor.BLUE);
        die5.setNumber(5);
        Die die6 = new Die(SagradaColor.BLUE);
        die6.setNumber(6);
        die1.decrease();
        die2.decrease();
        die3.decrease();
        die4.decrease();
        die5.decrease();
        die6.decrease();
        assertEquals(die1.getNumber(), 1);
        assertEquals(die2.getNumber(), 2 - 1);
        assertEquals(die3.getNumber(), 3 - 1);
        assertEquals(die4.getNumber(), 4 - 1);
        assertEquals(die5.getNumber(), 5 - 1);
        assertEquals(die6.getNumber(), 6 - 1);

    }


    @Test
    void testCheckValue() {
        for (int i = -1000; i <= +1000; i++) {
            if (i < Die.MIN || i > Die.MAX) {
                assertFalse(Die.checkValue(i));
            } else {
                assertTrue(Die.checkValue(i));
            }
        }
    }
}