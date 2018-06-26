package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.RMIClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends ServerInterface, Remote {

    @Override
    void start(String[] args) throws RemoteException;


    void login(Player p, RMIClientInterface c ) throws RemoteException;

    void patternCardValidation(String patternName, RMIClientInterface c) throws RemoteException;

    void validateMove(Die d, int row, int column, Player p) throws RemoteException;

    RMIServerInterface getNewStub() throws RemoteException;
}
