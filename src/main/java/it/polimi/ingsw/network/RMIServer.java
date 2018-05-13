package it.polimi.ingsw.network;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    List<Player> users= new ArrayList<Player>();
    GameManager gm ;

    protected RMIServer() throws RemoteException {
        gm= new GameManager();
    }

    @Override
    public void startGame() throws RemoteException {
    }

    @Override
    public void login(String name) throws RemoteException{
        Player player = new Player(name);
        if(!users.isEmpty() && users.contains(player)){
            System.err.println("Users already logged in");
        } else {
            users.add(player);
        }

        gm.addPlayer(player);
    }

    @Override
    public void notifyClients() throws RemoteException {
    }
}
