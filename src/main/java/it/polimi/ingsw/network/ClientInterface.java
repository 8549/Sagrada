package it.polimi.ingsw.network;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.List;

public interface ClientInterface extends Remote {
    String getName() throws RemoteException;

    void login() throws RemoteException;

    void pushData() throws RemoteException;

    void updatePlayersInfo(ClientInterface c) throws RemoteException;

    ObservableList<ClientInterface> getClients() throws RemoteException;

    void setCurrentLogged(List<ClientInterface> clients) throws RemoteException;
}
