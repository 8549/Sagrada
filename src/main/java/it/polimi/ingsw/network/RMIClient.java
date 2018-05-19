package it.polimi.ingsw.network;

import it.polimi.ingsw.Player;
import javafx.beans.property.ListProperty;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    public Player player;
    ListProperty<Player> players;

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
    public void updatePlayersInfo() throws RemoteException{

    }

    public ListProperty getPlayers() {
        return players;

    }
}
