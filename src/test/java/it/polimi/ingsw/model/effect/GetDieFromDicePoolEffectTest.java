package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.MainServer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetDieFromDicePoolEffectTest {

    @Test
    void testPerform() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.setToolCardHandlerbis(toolCardMock);
        GetDieFromDicePoolEffect getDieFromDicePoolEffect = new GetDieFromDicePoolEffect("getDieFromDicePool");
        getDieFromDicePoolEffect.setToolCard(toolCardMock);
        getDieFromDicePoolEffect.perform();
        assertTrue(toolCardMock.getDie()!=null);
    }

    private class ToolCardMock extends it.polimi.ingsw.model.ToolCard {
        Round round;
        Turn turn;
        Board board;
        Cell[][] grid;

        public void setToolCardMock() {
            List<Player> players = new ArrayList<>();
            Player marco = new Player("marco");
            players.add(marco);
            Player giulia = new Player("giulia");
            players.add(giulia);
            Player andrea = new Player("andrea");
            players.add(andrea);
            Player francesca = new Player("francesca");
            players.add(francesca);

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
            List<Effect> features = new ArrayList<>();
            Effect addDieToDicePoolEffect = new AddDieToDicePoolEffect("addDieToDicePool");
            Effect effect4 = new ChangeTurnOrderEffect("changeTurnOrder");
            Effect effect5 = new CheckIfDieHasBeenPlacedEffect("checkIfDieHasBeenPlaced");
            Effect effect6 = new CheckIsDiePlacedEffect("checkIsDiePlaced");
            Effect effect7= new CheckIsFirstTurnEffect("checkIsFirstTurn");
            Effect effect8 = new CheckIsSecondTurnEffect("checkIsSecondTurn");
            Effect effect = new DecreaseValueEffect("decreaseValueDie");
            Effect effect1 = new FlipDieEffect("flipDie");
            Effect effect2 = new GetDieFromDicePoolEffect("getDieFromDicePool");
            Effect effect3 = new IncreaseValueEffect("increaseValueDie");
            Effect effect9 = new PlaceDieInDraftPoolEffect("placeDieInDraftPool");
            Effect effect10 = new ReplaceDieOnRoundTrackEffect("replaceDieOnRoundTrack");
            Effect effect11 = new RollAllDiceEffect("rollAllDice");
            Effect effect12 = new RollDieEffect("rollDie");
            features.add(effect1);
            features.add(effect2);
            features.add(effect3);
            features.add(effect);
            features.add(effect2);
            features.add(effect3);
            features.add(addDieToDicePoolEffect);
            features.add(effect4);
            features.add(effect5);
            features.add(effect6);
            features.add(effect7);
            features.add(effect8);
            features.add(effect9);
            features.add(effect11);
            features.add(effect10);
            features.add(effect12);
            effectIterator = features.iterator();
            everythingOk=true;
        }


        public void setToolCardHandlerbis(ToolCardMock toolCard){
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

        @Override
        public void chooseDieFromWindowPattern() {
            toolcard.setResponse(true);
        }

        @Override
        public void chooseDieFromDraftPool() {
            toolcard.completeChooseDieFromDraftPool(toolcard.getBoard().getDraftPool().get(1));
        }

        @Override
        public void chooseDieFromRoundTrack() {
            toolcard.setResponse(true);
        }


        @Override
        public void chooseIfDecrease() {
            toolcard.setResponse(true);
        }

        @Override
        public void chooseIfPlaceDie() {
            toolcard.setResponse(true);
        }

        @Override
        public void chooseToMoveOneDie() {

        }



        public void setValue() { }

        public void chosenValue(int value){}

        public void setNewCoordinates() {}

        public void chooseTwoDieFromWindowPatter() {
        }

    }

}
