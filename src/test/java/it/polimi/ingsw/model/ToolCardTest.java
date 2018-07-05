package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.effect.*;
import it.polimi.ingsw.network.server.MainServer;
import it.polimi.ingsw.serialization.GsonSingleton;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.ManageReferralControl;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToolCardTest {
    @Test
    void testVerifyToolCard() {
        String name = "Tap Wheel";
        int id = 12;
        String when = "always";
        List<Effect> features = new ArrayList<>();
        Effect effect = new ChooseToMoveOneOrTwoDice("chooseToMoveOneOrTwoDice");
        Effect effect1;
        effect1 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
        Effect effect2 = new ChooseDieFromWindowPattern("chooseDieFromWindowPattern");
        Effect effect3 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
        features.add(effect1);
        features.add(effect2);
        features.add(effect3);
        features.add(effect);
        features.add(effect2);
        features.add(effect3);
        it.polimi.ingsw.model.ToolCard tapWheel = new it.polimi.ingsw.model.ToolCard();
        tapWheel.setParameters(name, id, when, features);
        CardsDeck deck = new CardsDeck("ToolCards.json", new TypeToken<List<it.polimi.ingsw.model.ToolCard>>() {
        }.getType());
        Card expectedCard = deck.getByName(name);
        Effect t = GsonSingleton.getInstance().fromJson("moveDieWithoutColorConstraint", Effect.class);
        assertEquals(expectedCard, tapWheel);
    }

    @Test
    void testCompleteChooseDieFromDraftPool() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        Die die = new Die(SagradaColor.RED);
        toolCardMock.completeChooseDieFromDraftPool(die);
        assertEquals(die, toolCardMock.die);
    }

    @Test
    void testCompleteChoiceIfDecrease() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.completeChoiceIfDecrease(true);
        assertTrue(toolCardMock.decrease);
        toolCardMock.completeChoiceIfDecrease(false);
        assertFalse(toolCardMock.decrease);
    }

    @Test
    void testCompleteChoiceIfPlaceDie() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.completeChoiceIfPlaceDie(true);
        assertTrue(toolCardMock.placeDie);
        toolCardMock.completeChoiceIfPlaceDie(false);
        assertFalse(toolCardMock.placeDie);
    }

    @Test
    void testCompleteChoiceIfMoveOneDie() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.completeChoiceIfMoveOneDie(true);
        assertTrue(toolCardMock.moveOneDie);
        toolCardMock.completeChoiceIfMoveOneDie(false);
        assertFalse(toolCardMock.moveOneDie);
    }

    @Test
    void testCompleteSetOldCoordinates() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.completeSetOldCoordinates(3, 4);
        assertEquals(3, toolCardMock.oldRow);
        assertEquals(4, toolCardMock.oldColumn);
    }

    @Test
    void testIsUsed() {
        it.polimi.ingsw.model.ToolCard toolCard = new it.polimi.ingsw.model.ToolCard();
        assertFalse(toolCard.isUsed());
    }

    @Test
    void testGetCost() {
        it.polimi.ingsw.model.ToolCard toolCard = new it.polimi.ingsw.model.ToolCard();
        assertEquals(1, toolCard.getCost());
    }

    @Test
    void testAddTokens() {
        it.polimi.ingsw.model.ToolCard toolCard = new it.polimi.ingsw.model.ToolCard();
        toolCard.addTokens();
        toolCard.addTokens();
        assertEquals(2, toolCard.getTokens());
    }

    @Test
    void testGetTokens() {
        it.polimi.ingsw.model.ToolCard toolCard = new it.polimi.ingsw.model.ToolCard();
        toolCard.addTokens();
        toolCard.addTokens();
        assertEquals(2, toolCard.getTokens());
    }

    @Test
    void testAddDieToDiceBag() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        Die die = toolCardMock.getBoard().getDiceBag().draftDie();
        toolCardMock.getBoard().getDiceBag().draftDie();
        assertTrue(toolCardMock.addDieToDiceBag(die));
    }

    @Test
    void testGetDieFromDicePool() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.getDieFromDicePool();
        assertFalse(toolCardMock.die == null);
    }

    @Test
    void testSetResponse() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.setResponse(true);
        assertTrue(toolCardMock.everythingOk);
        toolCardMock.setResponse(false);
        assertFalse(toolCardMock.everythingOk);
    }


    @Test
    void testProcessMoveWithoutConstraints() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.processMoveWithoutConstraints(true, false, true, false);
        assertTrue(toolCardMock.number);
        assertTrue(toolCardMock.adjacency);
        assertFalse(toolCardMock.color);
        assertFalse(toolCardMock.place);
    }

    @Test
    void testCompleteChooseDieFromWindowPattern() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.completeChooseDieFromWindowPattern(2, 4);
        assertEquals(toolCardMock.dice[6], toolCardMock.die);
    }

    @Test
    void testCompleteChooseValue() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        Die die = new Die(SagradaColor.RED);
        toolCardMock.completeChooseDieFromDraftPool(die);
        toolCardMock.completeChooseValue(2);
        assertEquals(2, toolCardMock.die.getNumber());
    }


    @Test
    void testGetTurn() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        assertEquals(toolCardMock.turn, toolCardMock.getTurn());
    }

    @Test
    void testGetRound() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        assertEquals(toolCardMock.round, toolCardMock.getRound());
    }

    @Test
    void testGetBoard() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        assertEquals(toolCardMock.board, toolCardMock.getBoard());
    }

    @Test
    void testGetDie() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        Die die = new Die(SagradaColor.RED);
        toolCardMock.completeChooseDieFromDraftPool(die);
        assertEquals(die, toolCardMock.die);
    }

    @Test
    void testSetParameters() {
        String name = "Tap Wheel";
        int id = 12;
        String when = "always";
        List<Effect> features = new ArrayList<>();
        Effect effect = new ChooseToMoveOneOrTwoDice("chooseToMoveOneOrTwoDice");
        Effect effect1;
        effect1 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
        Effect effect2 = new ChooseDieFromWindowPattern("chooseDieFromWindowPattern");
        Effect effect3 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
        features.add(effect1);
        features.add(effect2);
        features.add(effect3);
        features.add(effect);
        features.add(effect2);
        features.add(effect3);
        it.polimi.ingsw.model.ToolCard tapWheel = new it.polimi.ingsw.model.ToolCard();
        tapWheel.setParameters(name, id, when, features);
        assertEquals(name, tapWheel.getName());

    }

    @Test
    void testClearData() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.clearData();
        assertEquals(null, toolCardMock.die);
        assertEquals(-1, toolCardMock.turnForRoundTrack);
        assertEquals(-1, toolCardMock.numberOfDieForRoundTrack);
        assertEquals(-1, toolCardMock.oldColumn);
        assertEquals(-1, toolCardMock.oldRow);
        assertEquals(null, toolCardMock.effectIterator);
        assertTrue(toolCardMock.decrease);
        assertTrue(toolCardMock.placeDie);
        assertTrue(toolCardMock.number);
        assertTrue(toolCardMock.adjacency);
        assertTrue(toolCardMock.color);
        assertTrue(toolCardMock.place);
        assertTrue(toolCardMock.everythingOk);
        assertFalse(toolCardMock.moveOneDie);

    }

    @Test
    void testCompleteProcessMove() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.setToolCardHandlerbis(toolCardMock);
        Die die = toolCardMock.getBoard().getDraftPool().get(2);
        toolCardMock.completeChooseDieFromDraftPool(die);
        toolCardMock.processMoveWithoutConstraints(true, true, true, true);
        toolCardMock.completeProcessMove(0, 1);
        assertTrue(toolCardMock.everythingOk);


        toolCardMock.completeProcessMove(0, 4);
        assertTrue(toolCardMock.everythingOk);

    }

    @Test
    void testCheckHasNextEffect() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        while (toolCardMock.effectIterator.hasNext()){
            toolCardMock.checkHasNextEffect();
            assertTrue(toolCardMock.everythingOk);
            toolCardMock.effectIterator.next();
        }
        toolCardMock.checkHasNextEffect();
        toolCardMock.toolCardHandler= new ToolCardHandler(toolCardMock.player, null, toolCardMock.getServer(),toolCardMock);
        assertFalse(toolCardMock.toolCardHandler.isActive());
    }


    @Test
    void completeProcessTwoMoves() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.setToolCardHandlerbis(toolCardMock);
        Die die = toolCardMock.getBoard().getDraftPool().get(2);
        toolCardMock.completeChooseDieFromDraftPool(die);
        toolCardMock.processTwoMoveWithoutConstraints(true, true, true, true);
        toolCardMock.completeProcessTwoMoves(7, 1, 5, 3);
        assertFalse(toolCardMock.everythingOk);

        toolCardMock.completeChoiceIfMoveOneDie(true);
        toolCardMock.completeProcessTwoMoves(0, 1, 3, 4);
        assertTrue(toolCardMock.isEverythingOk());
    }

    @Test
    void testIsEverythingOk() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        assertTrue(toolCardMock.isEverythingOk());
        toolCardMock.setResponse(false);
        assertFalse(toolCardMock.isEverythingOk());
    }

    private class ToolCardMock extends it.polimi.ingsw.model.ToolCard {
        Round round;
        Turn turn;
        Board board;
        Cell[][] grid;
        Die[] dice;

        public void setToolCardMock() {
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
            grid = new Cell[WindowPattern.ROWS][WindowPattern.COLUMNS];
            int w = 0;
            for (int i = 0; i < WindowPattern.ROWS; i++) {
                for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                    grid[i][j] = new Cell();
                    w++;
                }
            }
            dice = new Die[8];
            dice[0] = new Die(SagradaColor.PURPLE);
            dice[0].setNumber(1);
            grid[0][3].setDie(dice[0]);
            dice[1] = new Die(SagradaColor.BLUE);
            dice[1].setNumber(2);
            grid[1][2].setDie(dice[1]);
            dice[2] = new Die(SagradaColor.YELLOW);
            dice[2].setNumber(4);
            grid[1][3].setDie(dice[2]);
            dice[3] = new Die(SagradaColor.GREEN);
            dice[3].setNumber(3);
            grid[1][4].setDie(dice[3]);
            dice[4] = new Die(SagradaColor.YELLOW);
            dice[4].setNumber(6);
            grid[2][2].setDie(dice[4]);
            dice[5] = new Die(SagradaColor.RED);
            dice[5].setNumber(6);
            grid[2][3].setDie(dice[5]);
            dice[6] = new Die(SagradaColor.RED);
            dice[6].setNumber(5);
            grid[2][4].setDie(dice[6]);
            dice[7] = new Die(SagradaColor.PURPLE);
            dice[7].setNumber(6);
            grid[3][4].setDie(dice[7]);
            List<Player> players = new ArrayList<>();
            Player marco = new Player("marco");
            players.add(marco);
            Player giulia = new Player("giulia");
            players.add(giulia);
            Player andrea = new Player("andrea");
            players.add(andrea);
            Player francesca = new Player("francesca");
            players.add(francesca);
            PlayerWindow playerWindow = new PlayerWindow();
            playerWindow.setWindowPattern(virtus);
            marco.getPlayerWindow().setWindowPattern(virtus);
            player = marco;
            board = new Board();
            board.setDiceBag();
            board.setPlayers(players);
            round = new Round(players, 1, board);
            turn = round.getTurn();
            board.setDraftPool(round.getDraftPool());
            name = "Tap Wheel";
            id = 12;
            when = "always";
            secondDie= board.getDraftPool().get(3);
            List<Effect> features = new ArrayList<>();
            Effect effect = new ChooseToMoveOneOrTwoDice("chooseToMoveOneOrTwoDice");
            Effect effect1;
            effect1 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
            Effect effect2 = new ChooseDieFromWindowPattern("chooseDieFromWindowPattern");
            Effect effect3 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
            features.add(effect1);
            features.add(effect2);
            features.add(effect3);
            features.add(effect);
            features.add(effect2);
            features.add(effect3);
            effectIterator = features.iterator();
            everythingOk=true;
        }


        public void setToolCardHandlerbis(ToolCard toolCard){
            MainServerMock mainServerMock = new MainServerMock();
            GameManager gm = new GameManager(board.getPlayers());
            toolCardHandler = new ToolCardHandlerMock(player, gm, mainServerMock, toolCard);
        }


        @Override
        public Round getRound() {
            return round;
        }

        @Override
        public Board getBoard() {
            return board;
        }

        @Override
        public Turn getTurn() {
            return turn;
        }

        @Override
        public void processMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place) {
            this.number = number;
            this.color = color;
            this.adjacency = adjacency;
            this.place = place;
        }

        @Override
        public void completeChooseDieFromWindowPattern(int oldRow, int oldColumn) {
            die = grid[oldRow][oldColumn].getDie();
            everythingOk = true;
        }

        @Override
        public void performEffect() {

        }

        @Override
        public MainServer getServer() {
            MainServerMock mainserver = new MainServerMock();
            return mainserver;
        }

        @Override
        public void checkHasNextEffect() {
        }

    }

    private class MainServerMock extends MainServer {
        public MainServerMock() {
            super();
        }

        @Override
        public void askPlayerForNextMove() {

        }

    }


    private class ToolCardHandlerMock extends ToolCardHandler {
        public ToolCardHandlerMock(Player p, GameManager gm, MainServer server, ToolCard toolCard) {
            super(p, gm, server, toolCard);
        }

        @Override
        public void updateDraftPool(List<Die> draft){}

        @Override
        public void notifyAddDie(Player player, Die d, int row, int column){}

        @Override
        public void notifyMoveDie(Player player, Die d, int row, int column, int newRow, int newColumn){}

        @Override
        public void notifyChangeTurn(Player first){}

        @Override
        public void updateRoundTrack(Die d, int diePosition, int round){}
    }

}


