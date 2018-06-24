package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.RMIClient;
import it.polimi.ingsw.network.client.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends ServerInterface, Remote {

    @Override
    void start(String[] args) throws RemoteException;


    void login(Player p, RMIClientInterface c ) throws RemoteException;

    void patternCardValidation(String patternName, RMIClientInterface c) throws RemoteException;

    RMIServerInterface getNewStub() throws RemoteException;
}
