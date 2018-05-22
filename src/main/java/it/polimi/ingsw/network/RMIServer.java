package it.polimi.ingsw.network;

import it.polimi.ingsw.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static it.polimi.ingsw.network.runServer.DEFAULT_RMI_PORT;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    ObservableList<ClientInterface> users = FXCollections.observableArrayList();
    ObservableList<ClientInterface> lobby = FXCollections.observableArrayList();

    GameManager gm ;

    protected RMIServer() throws RemoteException {
    }

    @Override
    public boolean start(String[] args) throws RemoteException {
        //RMI SERVER

        OptionParser parser = new OptionParser();
        parser.accepts("p").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_RMI_PORT);
        OptionSet set = parser.parse(args);

        int port = (int) set.valueOf("p");

        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(port);

            Naming.rebind("rmi://127.0.0.1:" + port + "/sagrada", this);
            return true;
        }catch (Exception e){
            System.out.println("[System] RMI Server failed: " + e);
        }
        return false;
    }

    @Override
    public void startGame() {
    }

    @Override
    public synchronized boolean login(ClientInterface client) {
        if(!users.isEmpty() && users.contains(client)){
            System.err.println("User already logged in");
            return false;
        } else {
            users.add(client);
            lobby.add(client);
            System.out.println("Current Players");
            try {
                client.setCurrentLogged(new ArrayList(users));
                for (ClientInterface c : lobby){
                    System.out.println("Player: " + c.getName());
                    if (!c.equals(client)) {
                        c.updatePlayersInfo(client);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }



            return true;
        }
    }

    @Override
    public boolean updateOtherServer() throws RemoteException {
        return false;
    }

    @Override
    public synchronized void checkRoom() {

    }

    @Override
    public ObservableList<ClientInterface> getClientsConnected() {
        return users;
    }

    @Override
    public String processInput(String input) throws RemoteException {
        return null;
    }

    @Override
    public synchronized void notifyClients() {
    }
}
