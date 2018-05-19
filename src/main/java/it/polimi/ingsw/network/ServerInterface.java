package it.polimi.ingsw.network;

import com.sun.deploy.util.SessionState;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void startGame() throws RemoteException;
    public boolean login(ClientInterface client) throws RemoteException;
    public void notifyClients() throws RemoteException;
    public void checkRoom() throws  RemoteException;
    public ObservableList<ClientInterface> getClientsConnected() throws RemoteException;
}
