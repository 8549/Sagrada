package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.*;
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
    public Player getPlayer()throws RemoteException;
    @Override
    public  void answerLogin(boolean response)throws RemoteException;
    @Override
    public void notifyEndTimeOut() throws RemoteException;
    @Override
    public void notifyEndTurn(Player p) throws RemoteException;
    @Override
    public void notifyEndRound(List<Die> dice) throws RemoteException;
    @Override
    public  void pushToolCards(List<String> tools)throws RemoteException;
    @Override
    public void chooseDieFromWindowPattern() throws RemoteException;
    @Override
    public void chooseDieFromDraftPool() throws RemoteException ;
    @Override
    public void chooseDieFromRoundTrack() throws RemoteException;
    @Override
    public void chooseIfDecrease() throws RemoteException;
    @Override
    public void chooseIfPlaceDie() throws RemoteException;
    @Override
    public void chooseToMoveOneDie() throws RemoteException;
    @Override
    public void setValue(String color) throws RemoteException;

    @Override
    public void setNewCoordinates() throws RemoteException;
    @Override
    public void notifyMoveNotAvailable() throws RemoteException;
    @Override
    public void nextMove() throws RemoteException;
    @Override
    public void notifyToolCardResponse(boolean response) throws RemoteException;
    @Override
    public void pushTokens(String name,String tool, int cost) throws RemoteException;
    @Override
    public void chooseTwoDice() throws RemoteException;
    @Override
    public void chooseTwoNewCoordinates() throws RemoteException;


    public void notifyToolUsed(boolean result, String name) throws RemoteException;


    public void moveDie(Player player,Die d, int row, int column, int newRow, int newColumn) throws RemoteException;
    public void addDie(Player player, Die d, int rowm, int column) throws RemoteException;
    public void changeTurn(Player first) throws RemoteException;

    public void updateRoundTrack(Die d, int diePosition, int round) throws RemoteException;

    public boolean ping() throws RemoteException;





}
