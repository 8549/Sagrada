package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.*;

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
    void notifyGameStarted(List<Player> players) throws RemoteException;
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
    void notifyMoveResponse(boolean response, String type) throws RemoteException;
    @Override
    public Player getPlayer()throws RemoteException;
    @Override
    public  void answerLogin(boolean response)throws RemoteException;
}
