package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.MainServer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddDieToDicePoolEffectTest {

    @Test
    void testPerform() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        toolCardMock.setToolCardHandlerbis(toolCardMock);
        Die die = toolCardMock.getBoard().getDraftPool().get(2);
        AddDieToDicePoolEffect addDieToDicePoolEffect = new AddDieToDicePoolEffect("addDieToDicePool");
        addDieToDicePoolEffect.setToolCard(toolCardMock);
        addDieToDicePoolEffect.perform(die);
        assertEquals(82, toolCardMock.getBoard().getDiceBag().getSize());
        assertEquals(8, toolCardMock.getBoard().getDraftPool().size());
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
            Effect effect = new ChooseToMoveOneOrTwoDice("chooseToMoveOneOrTwoDice");
            Effect effect1 =new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
            Effect effect2 = new ChooseDieFromWindowPattern("chooseDieFromWindowPattern");
            Effect effect3 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
            Effect addDieToDicePoolEffect = new AddDieToDicePoolEffect("addDieToDicePool");
            features.add(effect1);
            features.add(effect2);
            features.add(effect3);
            features.add(effect);
            features.add(effect2);
            features.add(effect3);
            features.add(addDieToDicePoolEffect);
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
    }


    private class MainServerMock extends MainServer {
        public MainServerMock() {
            super();
        }

        @Override
        public void askPlayerForNextMove() {

        }

    }

    private class ToolCardHandlerMock extends ToolCardHandler{
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