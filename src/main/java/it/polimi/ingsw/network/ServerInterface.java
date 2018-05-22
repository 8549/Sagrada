package it.polimi.ingsw.network;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    static ObservableList<ClientInterface> users = FXCollections.observableArrayList();
    static ObservableList<ClientInterface> lobby = FXCollections.observableArrayList();
    public boolean start(String[] args) throws IOException;
    public void startGame() throws RemoteException;
    public boolean login(ClientInterface client) throws RemoteException;
    public boolean updateOtherServer() throws RemoteException;
    public void notifyClients() throws RemoteException;
    public void checkRoom() throws  RemoteException;
    public ObservableList<ClientInterface> getClientsConnected() throws RemoteException;

    public String processInput(String input) throws IOException;
}
