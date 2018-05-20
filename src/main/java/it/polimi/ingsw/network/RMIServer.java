package it.polimi.ingsw.network;

import it.polimi.ingsw.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    ObservableList<ClientInterface> users = FXCollections.observableArrayList();
    ObservableList<ClientInterface> lobby = FXCollections.observableArrayList();

    GameManager gm ;

    protected RMIServer() throws RemoteException {
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
    public synchronized void checkRoom() {

    }

    @Override
    public ObservableList<ClientInterface> getClientsConnected() {
        return users;
    }

    @Override
    public synchronized void notifyClients() {
    }
}
