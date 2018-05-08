package it.polimi.ingsw.Comunication;

import it.polimi.ingsw.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    List<Player> users;
    List<ClientInterface> active;

    protected RMIServer() throws RemoteException {
    }

    @Override
    public void startGame() {
        System.out.println("Hello");
    }

    @Override
    public void acceptConnection() {
        System.out.println("Hello");
    }

    @Override
    public void notifyClients() {
        System.out.println("Hello");
    }
}
