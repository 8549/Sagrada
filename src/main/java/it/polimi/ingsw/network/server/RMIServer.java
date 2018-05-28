package it.polimi.ingsw.network.server;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.network.client.ClientInterface;
import javafx.collections.ObservableList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.network.runServer.DEFAULT_RMI_PORT;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    ObservableList<ClientWrapper> users ;
    ObservableList<ClientWrapper> lobby ;

    public RMIServer(ObservableList<ClientWrapper> users, ObservableList<ClientWrapper> lobby) throws RemoteException {
        this.users = users;
        this.lobby= lobby;
    }

    @Override
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
            System.out.println("[System] RMI Server is listening on port " + DEFAULT_RMI_PORT);

        }catch (Exception e){
            System.out.println("[System] RMI Server failed: " + e);
        }
    }

    @Override
    public void ping() {
    }

    @Override
    public synchronized boolean login(ClientWrapper client) throws RemoteException {
        if(!users.isEmpty() ){
            for (ClientWrapper c : users ){
                if (c.getName().equals(client.getName())){
                    System.err.println("User already logged in");
                    return false;
                }
            }

        }

        users.add(client);
        lobby.add(client);
        System.out.println("Current Players");
        /*try {
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
            return true;*/
        return false;

    }
    public synchronized boolean login(ClientInterface client) throws RemoteException{
       /* if(!users.isEmpty() ){
            for (ClientWrapper c : users ){
                if (c.getName().equals(client.getName())){
                    System.err.println("User already logged in");
                    return false;
                }
            }

        }

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
            return true;*/
       return true;

    }

    @Override
    public boolean updateOtherServer() throws RemoteException {
        return false;
    }


    @Override
    public ObservableList<ClientWrapper> getClientsConnected() {
        return users;
    }

    @Override
    public void removeClient(ClientWrapper c) throws RemoteException{
        return;

    }

    @Override
    public boolean checkTimer() throws RemoteException {
        return false;
    }


    @Override
    public synchronized void notifyClients() {
    }

    @Override
    public void showClients() throws RemoteException {

    }
}
