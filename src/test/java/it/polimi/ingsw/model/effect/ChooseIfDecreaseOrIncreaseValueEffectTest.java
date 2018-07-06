package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChooseIfDecreaseOrIncreaseValueEffectTest {

    @Test
    void testPerform() {
        ToolCardMock toolCardMock = new ToolCardMock();
        toolCardMock.setToolCardMock();
        ChooseIfDecreaseOrIncreaseValueEffect chooseIfDecreaseOrIncreaseValueEffect = new  ChooseIfDecreaseOrIncreaseValueEffect("chooseIfDecreaseOrIncreaseValue");;
        chooseIfDecreaseOrIncreaseValueEffect.setToolCard(toolCardMock);
        chooseIfDecreaseOrIncreaseValueEffect.perform();
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
            Effect effect = new ChooseToMoveOneOrTwoDiceEffect("chooseToMoveOneOrTwoDice");
            Effect effect1 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
            Effect effect3 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
            Effect addDieToDicePoolEffect = new AddDieToDicePoolEffect("addDieToDicePool");
            Effect effect4 = new ChooseDieFromDraftPoolEffect("chooseDieFromDraftPool");
            Effect effect5 = new ChooseDieFromRoundTrackEffect("chooseDieFromRoundTrack");
            Effect effect6 = new ChooseDieFromWindowPatternEffect("chooseDieFromWindowPattern");
            Effect effect7 = new ChooseDieValueEffect("chooseDieValue");
            Effect effect8 = new ChooseIfDecreaseOrIncreaseValueEffect("chooseIfDecreaseOrIncreaseValue");
            Effect effect9 = new ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect("chooseIfPlaceDieOrPlaceDieInDraftPool");
            Effect effect10 = new ChooseToMoveOneOrTwoDiceEffect("chooseToMoveOneOrTwoDice");
            Effect effect11 = new ChooseTwoDiceFromWindowPatterEffect("chooseTwoDiceFromWindowPattern");
            Effect effect12 = new MoveDieEffect("moveDie");
            Effect effect13 = new MoveDieWithoutColorConstraintEffect("moveDieWithoutColorConstraint");
            Effect effect14 = new MoveDieWithSameColorAsDieFromRoundTrackEffect("moveDieWithSameColorAsDieFromRoundTrack");
            Effect effect15 = new MoveDieWithoutNumberConstraintEffect("moveDieWithoutNumberConstraint");
            Effect effect16 = new PlaceDieEffect("placeDie");
            Effect effect17 = new PlaceDieWithoutAdjacencyConstraintEffect("placeDieInDraftPool");
            Effect effect18 = new PlaceDieInDraftPoolEffect("placeDieWithoutAdjacencyConstraint");
            features.add(effect1);
            features.add(effect3);
            features.add(effect);
            features.add(effect3);
            features.add(addDieToDicePoolEffect);
            features.add(effect4);
            features.add(effect5);
            features.add(effect6);
            features.add(effect7);
            features.add(effect8);
            features.add(effect9);
            features.add(effect10);
            features.add(effect10);
            features.add(effect11);
            features.add(effect12);
            features.add(effect13);
            features.add(effect14);
            features.add(effect15);
            features.add(effect16);
            features.add(effect17);
            features.add(effect18);
            effectIterator = features.iterator();
            everythingOk = true;

        }

        @Override
        public void chooseDieFromDraftPool() {
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


        public void getDieFromDicePool() {
            die = getBoard().getDiceBag().draftDie();
        }

        @Override
        public void chooseDieFromWindowPattern() {
            everythingOk= true;
        }

        @Override
        public void chooseTwoDieFromWindowPatter() {
            everythingOk= true;
        }


        @Override
        public void chooseDieFromRoundTrack() {
            everythingOk= true;
        }

        @Override
        public void chooseIfDecrease() {
            everythingOk= true;
        }

        @Override
        public void chooseIfPlaceDie() {
            everythingOk= true;
        }

        @Override
        public void chooseToMoveOneDie() {
            everythingOk= true;
        }


    }
}