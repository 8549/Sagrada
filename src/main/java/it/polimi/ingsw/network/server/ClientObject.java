package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.util.List;

public interface ClientObject {
    public void pushPlayers(List<Player> players) throws IOException;

    public void pushLoggedPlayer(Player player) throws IOException;

    public void notifyPlayerDisconnection(Player p) throws IOException;

    public void notifyGameStarted(List<Player> players, int timeout) throws IOException;

    public void requestPatternCardChoice(List<PatternCard> patternCards) throws IOException;

    public void pushPatternCardResponse(String name) throws IOException;

    public void pushOpponentsInit(List<Player> thinPlayers) throws IOException;

    public void pushPublicObj(PublicObjectiveCard[] publicObj) throws IOException;

    public void pushToolCards(List<String> tools) throws IOException;

    public void setPrivObj(String name, List<Player> players) throws IOException;

    public void pushDraft(List<Die> draft) throws IOException;

    public void notifyTurn(Player p, int round, int turn) throws IOException;

    public void notifyMoveResponse(boolean response, String name, Die d, int row, int column) throws IOException;

    public void notifyEndTimeOut() throws IOException;

    public void notifyEndTurn(Player p) throws IOException;

    public void notifyEndRound(List<Die> dice) throws IOException;

    public void nextMove() throws IOException;


    public void pushTokens(String name, String tool, int cost) throws IOException;

    public void notifyMoveNotAvailable() throws IOException;

    public Player getPlayer() throws IOException;

    public void answerLogin(boolean response) throws IOException;

    public void chooseDieFromWindowPattern() throws IOException;

    public void chooseDieFromDraftPool() throws IOException;

    public void chooseDieFromRoundTrack() throws IOException;

    public void chooseIfDecrease() throws IOException;

    public void chooseIfPlaceDie() throws IOException;

    public void chooseToMoveOneDie() throws IOException;

    public void setValue(String color) throws IOException;

    public void setNewCoordinates() throws IOException;

    public void chooseTwoDice() throws IOException;
    public void chooseTwoNewCoordinates() throws IOException;


    public void notifyToolUsed(boolean result, String name) throws IOException;


    public void moveDie(Player player, Die d, int row, int column, int newRow, int newColumn) throws IOException;
    public void addDie(Player player, Die d, int rowm, int column) throws IOException;
    public void changeTurn(Player first) throws IOException;

    public void updateRoundTrack(Die d, int diePosition, int round) throws IOException;

    public void updateGrid(int row, int col, Die d, String player ) throws IOException;

    public void pushFinalScore(List<Player> players) throws IOException;

    public void reconnection() throws IOException;

}