package it.polimi.ingsw.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    public String Name;
    protected RMIClient() throws RemoteException {
    }

    @Override
    public void connect() throws RemoteException{

    }

    @Override
    public void pushData() throws RemoteException{

    }

    @Override
    public void updatePlayersInfo() throws RemoteException{

    }
}
