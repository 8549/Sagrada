package it.polimi.ingsw.Comunication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
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
