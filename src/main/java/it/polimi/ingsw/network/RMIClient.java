package it.polimi.ingsw.network;

import it.polimi.ingsw.Player;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    public Player player;
    ListProperty<ClientInterface> clients;

    public RMIClient(String name) throws RemoteException {
        player = new Player(name);
        clients = new SimpleListProperty<>();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void connect() {

    }

    @Override
    public void pushData() {

    }

    @Override
    public void updatePlayersInfo(ClientInterface c) {
        clients.add(c);

    }

    @Override
    public ListProperty<ClientInterface> getClients() {
        return clients;

    }
}
