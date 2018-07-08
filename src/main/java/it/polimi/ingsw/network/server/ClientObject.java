package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PublicObjectiveCard;

import java.io.IOException;
import java.util.List;

public interface ClientObject {
    void pushPlayers(List<Player> players) throws IOException;

    void pushLoggedPlayer(Player player) throws IOException;

    void notifyPlayerDisconnection(Player p) throws IOException;

    void notifyGameStarted(List<Player> players, int timeout) throws IOException;

    void requestPatternCardChoice(List<PatternCard> patternCards) throws IOException;

    void pushPatternCardResponse(String name) throws IOException;

    void pushOpponentsInit(List<Player> thinPlayers) throws IOException;

    void pushPublicObj(PublicObjectiveCard[] publicObj) throws IOException;

    void pushToolCards(List<String> tools) throws IOException;

    void setPrivObj(String name, List<Player> players) throws IOException;

    void pushDraft(List<Die> draft) throws IOException;

    void notifyTurn(Player p, int round, int turn) throws IOException;

    void notifyMoveResponse(boolean response, String name, Die d, int row, int column) throws IOException;

    void notifyEndTimeOut() throws IOException;

    void notifyEndTurn(Player p) throws IOException;

    void notifyEndRound(List<Die> dice) throws IOException;

    void nextMove() throws IOException;


    void pushTokens(String name, String tool, int cost) throws IOException;

    void notifyMoveNotAvailable() throws IOException;

    Player getPlayer() throws IOException;

    void answerLogin(boolean response) throws IOException;

    void chooseDieFromWindowPattern() throws IOException;

    void chooseDieFromDraftPool() throws IOException;

    void chooseDieFromRoundTrack() throws IOException;

    void chooseIfDecrease() throws IOException;

    void chooseIfPlaceDie(int number) throws IOException;

    void chooseToMoveOneDie() throws IOException;

    void setValue(String color) throws IOException;

    void setNewCoordinates() throws IOException;

    void chooseTwoDice() throws IOException;

    void chooseTwoNewCoordinates() throws IOException;


    void notifyToolUsed(boolean result, String name) throws IOException;


    void moveDie(Player player, Die d, int row, int column, int newRow, int newColumn) throws IOException;

    void addDie(Player player, Die d, int rowm, int column) throws IOException;

    void changeTurn(Player first) throws IOException;

    void updateRoundTrack(Die d, int diePosition, int round) throws IOException;

    void updateGrid(int row, int col, Die d, String player) throws IOException;

    void pushFinalScore(List<Player> players) throws IOException;

    void reconnection() throws IOException;

}