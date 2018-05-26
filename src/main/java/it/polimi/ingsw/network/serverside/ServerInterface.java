package it.polimi.ingsw.network.serverside;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public void start(String[] args) throws IOException;
    public void ping() throws RemoteException;
    public boolean login(ClientWrapper client) throws RemoteException;
    public boolean updateOtherServer() throws RemoteException;
    public void notifyClients() throws RemoteException;
    public void showClients() throws  RemoteException;
    public ObservableList<ClientWrapper> getClientsConnected() throws RemoteException;
    public void removeClient(ClientWrapper c) throws RemoteException;

}
