package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PublicObjectiveCard;
import it.polimi.ingsw.network.server.ClientObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIClientObjectInterface extends ClientObject, Remote {
    @Override
    void pushPlayers(List<Player> players) throws RemoteException;
    @Override
    void pushLoggedPlayer(Player player) throws RemoteException;
    @Override
    void notifyPlayerDisconnection(Player p) throws RemoteException;
    @Override
    void notifyGameStarted(List<Player> players, int timeout) throws RemoteException;
    @Override
    void requestPatternCardChoice(List<PatternCard> patternCards) throws RemoteException;
    @Override
    void pushPatternCardResponse(String name) throws RemoteException;
    @Override
    void pushOpponentsInit(List<Player> thinPlayers) throws RemoteException;
    @Override
    void pushPublicObj(PublicObjectiveCard[] publicObj) throws RemoteException;
    @Override
    void setPrivObj(String name, List<Player> players) throws RemoteException;
    @Override
    void pushDraft(List<Die> draft) throws RemoteException;
    @Override
    void notifyTurn(Player p, int round, int turn) throws RemoteException;
    @Override
    void notifyMoveResponse(boolean response, String name, Die d, int row, int column) throws RemoteException;
    @Override
    Player getPlayer() throws RemoteException;
    @Override
    void answerLogin(boolean response) throws RemoteException;
    @Override
    void notifyEndTimeOut() throws RemoteException;
    @Override
    void notifyEndTurn(Player p) throws RemoteException;
    @Override
    void notifyEndRound(List<Die> dice) throws RemoteException;
    @Override
    void pushToolCards(List<String> tools) throws RemoteException;
    @Override
    void chooseDieFromWindowPattern() throws RemoteException;
    @Override
    void chooseDieFromDraftPool() throws RemoteException;
    @Override
    void chooseDieFromRoundTrack() throws RemoteException;
    @Override
    void chooseIfDecrease() throws RemoteException;
    @Override
    void chooseIfPlaceDie(int number) throws RemoteException;
    @Override
    void chooseToMoveOneDie() throws RemoteException;
    @Override
    void setValue(String color) throws RemoteException;

    @Override
    void setNewCoordinates() throws RemoteException;
    @Override
    void notifyMoveNotAvailable() throws RemoteException;
    @Override
    void nextMove() throws RemoteException;

    @Override
    void pushTokens(String name, String tool, int cost) throws RemoteException;
    @Override
    void chooseTwoDice() throws RemoteException;
    @Override
    void chooseTwoNewCoordinates() throws RemoteException;


    void notifyToolUsed(boolean result, String name) throws RemoteException;


    void moveDie(Player player, Die d, int row, int column, int newRow, int newColumn) throws RemoteException;

    void addDie(Player player, Die d, int rowm, int column) throws RemoteException;

    void changeTurn(Player first) throws RemoteException;

    void updateRoundTrack(Die d, int diePosition, int round) throws RemoteException;

    void updateGrid(int row, int col, Die d, String name) throws RemoteException;

    void reconnection() throws RemoteException;

    void notifyFinishUpdate() throws RemoteException;

    void pushFinalScore(List<Player> players) throws RemoteException;


    boolean ping() throws RemoteException;





}
