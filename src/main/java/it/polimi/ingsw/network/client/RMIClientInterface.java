package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.RMI.RMIServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIClientInterface extends ClientInterface, Remote {
    @Override
    void connect(String serverAddress, int portNumber, String userName) throws RemoteException;

    @Override
    String getName() throws RemoteException;

    @Override
    void requestPlacement(int number, String color, int row, int column)throws RemoteException;

    void loginResponse(boolean result) throws RemoteException;

    void addPlayersToProxy(List<Player> players) throws RemoteException;

    void addPlayerToProxy(Player player)throws RemoteException;

    void initPatternCardChoice(List<PatternCard> choices) throws RemoteException;

    void initGame(List<Player> p, int timeout) throws RemoteException;

    void setPrivateObj(String name) throws RemoteException;

    void setPublicObj(List<PublicObjectiveCard> publicObjCards) throws RemoteException;
    void setDraft(List<Die> draft) throws RemoteException;
    void beginTurn(String name, int round, int turn) throws RemoteException;
    void patternCardResponse(String name)throws RemoteException;

    void initTools(List<String> names) throws RemoteException;

    void setEndPoint(RMIServerInterface server) throws RemoteException;
    void updateOpponentsInfo(List<Player> players) throws RemoteException;
    void moveResponse(boolean response) throws RemoteException;
    void moveTimeOut()throws RemoteException;
    void endCurrentTurn(String name) throws RemoteException;

}
