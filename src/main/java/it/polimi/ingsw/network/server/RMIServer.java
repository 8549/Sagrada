package it.polimi.ingsw.network.server;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.RMIClient;
import it.polimi.ingsw.network.client.RMIClientInterface;
import javafx.collections.ObservableList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import sun.applet.Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.server.MainServer.DEFAULT_RMI_PORT;

public class RMIServer  implements RMIServerInterface {
    List<ClientObject> users ;
    MainServer server;
    RMIServerInterface stub;
    List<RMIServerInterface> objs= new ArrayList<>();

    public RMIServer(List<ClientObject> users, MainServer server) throws RemoteException {
        this.users = users;
        this.server = server;
    }

    @Override
    public void start(String[] args) throws RemoteException {
        //RMI SERVER
       /* OptionParser parser = new OptionParser();
        parser.accepts("p").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_RMI_PORT);
        OptionSet set = parser.parse(args);

        int port = (int) set.valueOf("p");*/


        try {

            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(this, 0);

            System.out.println("IP addres of server: " + InetAddress.getLocalHost().getHostAddress());
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());


            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("RMIServerInterface", stub);

            System.out.println("[DEBUG] RMI server is ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }


    }

    public RMIServerInterface getEndPoint() throws RemoteException {
        RMIServerInterface obj = new RMIServerInstance(users, server);
        RMIServerInterface  stub = (RMIServerInterface) UnicastRemoteObject.exportObject(obj, 0);
        objs.add(stub);
        return stub;
    }

    public RMIServerInterface getStub(){
        return this.stub;
    }

    @Override
    public void login(Player p, RMIClientInterface c) throws RemoteException {
        RMIClientObjectInterface client = new RMIClientObject(p, c);
        RMIClientObjectInterface clientstub = (RMIClientObjectInterface) UnicastRemoteObject.exportObject(client,0);

        boolean result= server.addClient(clientstub);

        clientstub.answerLogin(result);


        if(result){
            server.addAlreadyLoogedPlayers(clientstub);
            server.addLoggedPlayer(clientstub.getPlayer());

            server.checkTimer();

        }
    }

    @Override
    public void patternCardValidation(String patternName, RMIClientInterface c) throws RemoteException {
        ClientObject client=null;
        for(ClientObject clients: users){
            try {
                if(clients.getPlayer().getName().equals(c.getName())){
                    client = clients;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        server.setPlayerChoice(client, patternName);
    }

    @Override
    public RMIServerInterface getNewStub() throws RemoteException {
        return getEndPoint();
    }

}
