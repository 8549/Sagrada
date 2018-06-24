package it.polimi.ingsw.network.server;

import java.rmi.RemoteException;
import java.util.List;

public class RMIServerInstance extends RMIServer {
    public RMIServerInstance(List<ClientObject> users, MainServer server) throws RemoteException {
        super(users, server);
    }
}
