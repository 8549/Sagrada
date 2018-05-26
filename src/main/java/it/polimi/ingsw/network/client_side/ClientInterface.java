package it.polimi.ingsw.network.client_side;

import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote {
    String getName() throws RemoteException;

    void login() throws RemoteException;

    void pushData() throws RemoteException;

    void updatePlayersInfo(ClientInterface c) throws RemoteException;

    ObservableList<ClientInterface> getClients() throws RemoteException;

    void setCurrentLogged(List<ClientInterface> clients) throws RemoteException;
}
