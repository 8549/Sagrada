package it.polimi.ingsw.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends ServerInterface, Remote {

    public void start(String[] args) throws RemoteException;
}
