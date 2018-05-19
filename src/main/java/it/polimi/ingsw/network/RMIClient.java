package it.polimi.ingsw.network;

import it.polimi.ingsw.Player;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    public Player player;
    ObservableList<ClientInterface> clients;

    public RMIClient(String name) throws RemoteException {
        player= new Player(name);
    }

    @Override
    public String getName() throws RemoteException {
        return player.getName();
    }

    @Override
    public void connect() throws RemoteException{

    }

    @Override
    public void pushData() throws RemoteException{

    }

    @Override
    public void updatePlayersInfo(ClientInterface c) throws RemoteException{
        clients.add(c);

    }

    @Override
    public ObservableList<ClientInterface> getClients() {
        return clients;

    }
}
