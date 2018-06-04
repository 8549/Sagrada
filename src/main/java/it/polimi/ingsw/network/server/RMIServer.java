package it.polimi.ingsw.network.server;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.ClientInterface;
import javafx.collections.ObservableList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static it.polimi.ingsw.network.server.MainServer.DEFAULT_RMI_PORT;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    ObservableList<ClientObject> users ;
    ObservableList<ClientObject> lobby ;

    public RMIServer(ObservableList<ClientObject> users, ObservableList<ClientObject> lobby) throws RemoteException {
        this.users = users;
        this.lobby= lobby;
    }

    /*@Override
    public void start(String[] args) throws RemoteException {
        //RMI SERVER

        OptionParser parser = new OptionParser();
        parser.accepts("p").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_RMI_PORT);
        OptionSet set = parser.parse(args);

        int port = (int) set.valueOf("p");

        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(DEFAULT_RMI_PORT);

            System.out.println("IP addres of server: " + InetAddress.getLocalHost().getHostAddress());
            //System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":" + DEFAULT_RMI_PORT + "/sagrada", this);
            System.out.println("[System] RMI MainServer is listening on port " + DEFAULT_RMI_PORT);

        }catch (Exception e){
            System.out.println("[System] RMI MainServer failed: " + e);
        }
    }*/



    @Override
    public void start() throws IOException {

    }
}
