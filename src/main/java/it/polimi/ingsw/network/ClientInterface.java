package it.polimi.ingsw.network;

import javafx.beans.property.ListProperty;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    String getName() throws RemoteException;

    void connect() throws RemoteException;

    void pushData() throws RemoteException;

    void updatePlayersInfo(ClientInterface c) throws RemoteException;

    ListProperty<ClientInterface> getClients() throws RemoteException;
}
