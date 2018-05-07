package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    @Test
    void getNumber() {

    }

    @Test
    void getColor() {
    }

    @Test
    void roll() {

    }

    @Test
    void setNumber() {
        Die die = new Die(SagradaColor.BLUE.getColor());
        int number = 2;
        die.setNumber(number);
        assertEquals(die.getNumber(), number);

    }

    @Test
    void flip() {
        Die die1 = new Die(SagradaColor.BLUE.getColor());
        die1.setNumber(1);
        Die die2 = new Die(SagradaColor.BLUE.getColor());
        die2.setNumber(2);
        Die die3 = new Die(SagradaColor.BLUE.getColor());
        die3.setNumber(3);
        Die die4 = new Die(SagradaColor.BLUE.getColor());
        die4.setNumber(4);
        Die die5 = new Die(SagradaColor.BLUE.getColor());
        die5.setNumber(5);
        Die die6 = new Die(SagradaColor.BLUE.getColor());
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
    void increase() {
        Die die1 = new Die(SagradaColor.BLUE.getColor());
        die1.setNumber(1);
        Die die2 = new Die(SagradaColor.BLUE.getColor());
        die2.setNumber(2);
        Die die3 = new Die(SagradaColor.BLUE.getColor());
        die3.setNumber(3);
        Die die4 = new Die(SagradaColor.BLUE.getColor());
        die4.setNumber(4);
        Die die5 = new Die(SagradaColor.BLUE.getColor());
        die5.setNumber(5);
        Die die6 = new Die(SagradaColor.BLUE.getColor());
        die6.setNumber(6);
        die1.increase();
        die2.increase();
        die3.increase();
        die4.increase();
        die5.increase();
        die6.increase();
        assertEquals(die1.getNumber(), 1+1);
        assertEquals(die2.getNumber(), 2+1);
        assertEquals(die3.getNumber(), 3+1);
        assertEquals(die4.getNumber(), 4+1);
        assertEquals(die5.getNumber(), 5+1);
        assertEquals(die6.getNumber(), 6);
    }



    @Test
    void decrease() {
        Die die1 = new Die(SagradaColor.BLUE.getColor());
        die1.setNumber(1);
        Die die2 = new Die(SagradaColor.BLUE.getColor());
        die2.setNumber(2);
        Die die3 = new Die(SagradaColor.BLUE.getColor());
        die3.setNumber(3);
        Die die4 = new Die(SagradaColor.BLUE.getColor());
        die4.setNumber(4);
        Die die5 = new Die(SagradaColor.BLUE.getColor());
        die5.setNumber(5);
        Die die6 = new Die(SagradaColor.BLUE.getColor());
        die6.setNumber(6);
        die1.decrease();
        die2.decrease();
        die3.decrease();
        die4.decrease();
        die5.decrease();
        die6.decrease();
        assertEquals(die1.getNumber(), 1);
        assertEquals(die2.getNumber(), 2-1);
        assertEquals(die3.getNumber(), 3-1);
        assertEquals(die4.getNumber(), 4-1);
        assertEquals(die5.getNumber(), 5-1);
        assertEquals(die6.getNumber(), 6-1);

    }


@Test
    void checkValue(){
            }
            }