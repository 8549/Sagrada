package it.polimi.ingsw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    List<Player> users;
    List<ClientInterface> active;

    protected RMIServer() throws RemoteException {
        startGame();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void acceptConnection() {

    }

    @Override
    public void notifyClients() {

    }
}
