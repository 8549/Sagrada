package it.polimi.ingsw.Comunication;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public void connect() throws RemoteException;
    public void pushData() throws RemoteException;
    public void updatePlayersInfo() throws RemoteException;

}
