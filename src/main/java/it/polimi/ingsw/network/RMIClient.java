package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {
    public Player player;
    ObservableList<ClientInterface> clients = FXCollections.observableArrayList();
    ServerInterface server;

    public RMIClient(String name, String hostName) throws RemoteException {
        player = new Player(name);
        try {
            server = (ServerInterface) Naming.lookup("rmi://" + hostName + "/sagrada");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void login() {
        try {
            if (server.login(this)) {
                System.out.println("Login accepted");
            } else {
                System.err.println("Login failed");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pushData() {

    }

    @Override
    public void updatePlayersInfo(ClientInterface c) {
        clients.add(c);

    }

    @Override
    public ObservableList<ClientInterface> getClients() {
        return clients;

    }

    @Override
    public void setCurrentLogged(List<ClientInterface> c) {
        clients.addAll(c);

    }
}
