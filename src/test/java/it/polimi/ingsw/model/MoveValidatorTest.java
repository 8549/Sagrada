package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveValidatorTest {
    @Test
    void testValidateMove() {
        List<Player> players = new ArrayList<>();
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Die[] draftPool1 = new Die[7];
        draftPool1[0]= new Die(SagradaColor.YELLOW);
        draftPool1[0].setNumber(1);
        draftPool1[1]= new Die(SagradaColor.PURPLE);
        draftPool1[1].setNumber(3);
        draftPool1[2]= new Die(SagradaColor.BLUE);
        draftPool1[2].setNumber(4);
        draftPool1[3]= new Die(SagradaColor.YELLOW);
        draftPool1[3].setNumber(5);
        draftPool1[4]= new Die(SagradaColor.GREEN);
        draftPool1[4].setNumber(6);
        draftPool1[5]= new Die(SagradaColor.PURPLE);
        draftPool1[5].setNumber(4);
        draftPool1[6]= new Die(SagradaColor.RED);
        draftPool1[6].setNumber(1);
        List<Die> draftPool = new ArrayList<>();
        for(int i=0; i<7; i++){
            draftPool.add(draftPool1[i]);
        }
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);
        Die[] dice = new Die[8];
        dice[0] = new Die(SagradaColor.RED);
        dice[0].setNumber(5);
        marco.getPlayerWindow().getDiceGrid()[0][3].setDie(dice[0]);
        dice[1] = new Die(SagradaColor.YELLOW);
        dice[1].setNumber(6);
        marco.getPlayerWindow().getDiceGrid()[1][2].setDie(dice[1]);
        dice[2] = new Die(SagradaColor.GREEN);
        dice[2].setNumber(3);
        marco.getPlayerWindow().getDiceGrid()[1][3].setDie(dice[2]);
        dice[3] = new Die(SagradaColor.PURPLE);
        dice[3].setNumber(2);
        marco.getPlayerWindow().getDiceGrid()[1][4].setDie(dice[3]);
        dice[4] = new Die(SagradaColor.GREEN);
        dice[4].setNumber(2);
        marco.getPlayerWindow().getDiceGrid()[2][2].setDie(dice[4]);
        dice[5] = new Die(SagradaColor.RED);
        dice[5].setNumber(4);
        marco.getPlayerWindow().getDiceGrid()[2][3].setDie(dice[5]);
        dice[6] = new Die(SagradaColor.BLUE);
        dice[6].setNumber(1);
        marco.getPlayerWindow().getDiceGrid()[2][4].setDie(dice[6]);
        dice[7] = new Die(SagradaColor.PURPLE);
        dice[7].setNumber(3);
        Turn turnMarco = new Turn(marco, 2);
        marco.getPlayerWindow().setWindowPattern(virtus);
        marco.getPlayerWindow().getDiceGrid()[3][3].setDie(dice[7]);
        boolean check = patternConstraints[0][4].checkConstraint(draftPool.get(0), CheckModifier.NOCOLOR);
        MoveValidator moveValidator1 = new MoveValidator(turnMarco, true, false, true);
        assertTrue(moveValidator1.validateMove(draftPool.get(0),0, 4, marco));
        MoveValidator moveValidator = new MoveValidator(turnMarco, true, true, true);
        assertTrue(moveValidator.validateMove(draftPool.get(3), 3, 4, marco));
        MoveValidator moveValidator2 = new MoveValidator(turnMarco, false, true,  true);
        assertFalse(moveValidator2.validateMove(draftPool.get(0),0, 4, marco));
        MoveValidator moveValidator6 = new MoveValidator(turnMarco, true, false, true);
        assertFalse(moveValidator6.validateMove(draftPool.get(0),6, 4, marco));
        }


    }
