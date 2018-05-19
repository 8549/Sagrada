package it.polimi.ingsw.network;

import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public String getName() throws RemoteException;
    public void connect() throws RemoteException;
    public void pushData() throws RemoteException;
    public void updatePlayersInfo(ClientInterface c) throws RemoteException;
    ObservableList<ClientInterface> getClients() throws RemoteException;
}
