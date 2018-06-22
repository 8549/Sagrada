package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIClientInterface extends ClientInterface, Remote {
    @Override
    void connect(String serverAddress, int portNumber, String userName) throws RemoteException;

    @Override
    String getName() throws RemoteException;

    void loginResponse(boolean result) throws RemoteException;

    void addPlayersToProxy(List<Player> players) throws RemoteException;

    void addPlayerToProxy(Player player)throws RemoteException;

    void initPatternCardChoice(List<PatternCard> choices) throws RemoteException;

    void initGame(List<Player> p) throws RemoteException;
}
