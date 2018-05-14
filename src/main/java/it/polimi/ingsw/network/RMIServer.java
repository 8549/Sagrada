package it.polimi.ingsw.network;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.Player;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    ObservableList<Player> users = new SimpleListProperty<>();
    GameManager gm ;

    protected RMIServer() throws RemoteException {
        gm= new GameManager();
    }

    @Override
    public void startGame() {
    }

    @Override
    public void login(String name) {
        Player player = new Player(name);
        if(!users.isEmpty() && users.contains(player)){
            System.err.println("Users already logged in");
        } else {
            users.add(player);
        }

        gm.addPlayer(player);
    }

    @Override
    public void notifyClients() {
    }
}
