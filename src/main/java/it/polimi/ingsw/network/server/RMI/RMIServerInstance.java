package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;

import java.rmi.RemoteException;
import java.util.List;

public class RMIServerInstance extends RMIServer {
    public RMIServerInstance(List<ClientObject> users, MainServer server) throws RemoteException {
        super(users, server);
    }
}
