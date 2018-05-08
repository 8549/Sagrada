package it.polimi.ingsw.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    protected RMIClient() throws RemoteException {
    }

    @Override
    public void connect() {

    }

    @Override
    public void pushData() {

    }

    @Override
    public void updatePlayersInfo() {

    }
}
