package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckIfDieHasBeenPlacedEffectTest {

    @Test
    void testPerform() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        CheckIfDieHasBeenPlacedEffect checkIfDieHasBeenPlacedEffect= new CheckIfDieHasBeenPlacedEffect("checkIfDieHasBeenPlaced");
        checkIfDieHasBeenPlacedEffect.setToolCard(toolCardMock);
        checkIfDieHasBeenPlacedEffect.perform(toolCardMock.getRound().getTurns().get(1));
        assertFalse(toolCardMock.isEverythingOk());
        toolCardMock.round.getTurn().setDiePlaced();
        checkIfDieHasBeenPlacedEffect.perform(toolCardMock.round.getTurn());
        assertTrue(toolCardMock.isEverythingOk());
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