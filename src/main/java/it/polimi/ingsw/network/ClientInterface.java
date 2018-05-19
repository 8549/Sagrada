package it.polimi.ingsw.network;

import javafx.beans.property.ListProperty;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public String getName() throws RemoteException;
    public void connect() throws RemoteException;
    public void pushData() throws RemoteException;
    public void updatePlayersInfo() throws RemoteException;
    public ListProperty getPlayers() throws RemoteException;

}
