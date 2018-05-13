package it.polimi.ingsw.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void startGame() throws RemoteException;
    public void login(String name) throws RemoteException;
    public void notifyClients() throws RemoteException;

}
