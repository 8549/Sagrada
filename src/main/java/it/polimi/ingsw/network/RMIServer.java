package it.polimi.ingsw.network;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.Player;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    ObservableList<Player> users = FXCollections.observableArrayList();
    List<Player> lobby = new ArrayList<Player>();

    GameManager gm ;

    protected RMIServer() throws RemoteException {
    }

    @Override
    public void startGame() {
    }

    @Override
    public synchronized boolean login(String name) {
        Player player = new Player(name);
        if(!users.isEmpty() && users.contains(player)){
            System.err.println("Users already logged in");
            return false;
        } else {
            users.add(player);
            lobby.add(player);
            System.out.println("Current Players ");
            for (Player p : lobby){
                System.out.println("Player : " + p.getName());
            }
            checkRoom();
            return true;
        }
    }

    @Override
    public synchronized void checkRoom() {

    }

    @Override
    public synchronized void notifyClients() {
    }
}
