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

    void pushPlayers(List<Player> players) throws RemoteException;

    void pushLoggedPlayer(Player player) throws RemoteException;

    void notifyPlayerDisconnection(Player p) throws RemoteException;

    void notifyGameStarted(List<Player> players, int timeout) throws RemoteException;

    void requestPatternCardChoice(List<PatternCard> patternCards) throws RemoteException;

    void pushPatternCardResponse(String name) throws RemoteException;

    void pushOpponentsInit(List<Player> thinPlayers) throws RemoteException;

    void pushPublicObj(PublicObjectiveCard[] publicObj) throws RemoteException;

    void setPrivObj(String name, List<Player> players) throws RemoteException;

    void pushDraft(List<Die> draft) throws RemoteException;

    void notifyTurn(Player p, int round, int turn) throws RemoteException;

    void notifyMoveResponse(boolean response, String name, Die d, int row, int column) throws RemoteException;

    Player getPlayer() throws RemoteException;

    void answerLogin(boolean response) throws RemoteException;

    void notifyEndTimeOut(Player p) throws RemoteException;

    void notifyEndTurn(Player p) throws RemoteException;

    void notifyEndRound(List<Die> dice) throws RemoteException;

    void pushToolCards(List<String> tools) throws RemoteException;

    void chooseDieFromWindowPattern() throws RemoteException;

    void chooseDieFromDraftPool() throws RemoteException;

    void chooseDieFromRoundTrack() throws RemoteException;

    void chooseIfDecrease() throws RemoteException;

    void chooseIfPlaceDie(int number) throws RemoteException;

    void chooseToMoveOneDie() throws RemoteException;

    void setValue(String color) throws RemoteException;


    void setNewCoordinates() throws RemoteException;

    void notifyMoveNotAvailable() throws RemoteException;

    void nextMove() throws RemoteException;


    void pushTokens(String name, String tool, int cost) throws RemoteException;

    void chooseTwoDice() throws RemoteException;

    void chooseTwoNewCoordinates() throws RemoteException;


    void notifyToolUsed(boolean result, String name) throws RemoteException;


    void moveDie(Player player, Die d, int row, int column, int newRow, int newColumn) throws RemoteException;

    void addDie(Player player, Die d, int rowm, int column) throws RemoteException;

    void changeTurn(Player first) throws RemoteException;

    void updateRoundTrack(Die d, int diePosition, int round) throws RemoteException;

    void updateGrid(int row, int col, Die d, String name) throws RemoteException;

    void reconnection() throws RemoteException;

    void notifyFinishUpdate(String name) throws RemoteException;

    void pushFinalScore(List<Player> players) throws RemoteException;


    boolean ping() throws RemoteException;


}
