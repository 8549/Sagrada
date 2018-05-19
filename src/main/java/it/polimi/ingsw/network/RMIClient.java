package it.polimi.ingsw.network;

import it.polimi.ingsw.Player;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    public Player player;
    ObservableList<ClientInterface> clients = FXCollections.observableArrayList();

    public RMIClient(String name) throws RemoteException {
        player = new Player(name);
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
    public ObservableList<ClientInterface> getClients() {
        return clients;

    }

    @Override
    public void setCurrentLogged(List<ClientInterface> c) throws RemoteException {
        clients.addAll(c);

    }
}
