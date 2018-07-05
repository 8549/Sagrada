package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.MainServer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddDieToDicePoolEffectTest {
/*
    @Test
    void testPerform() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        Die die = toolCardMock.getBoard().getDraftPool().get(2);
        AddDieToDicePoolEffect addDieToDicePoolEffect = new AddDieToDicePoolEffect("addDieToDicePool");
        addDieToDicePoolEffect.setToolCard(toolCardMock);
        addDieToDicePoolEffect.perform(die);
        assertEquals(82, toolCardMock.getBoard().getDiceBag().getSize());
        assertEquals(8, toolCardMock.getBoard().getDraftPool().size());
    }
*/
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
}