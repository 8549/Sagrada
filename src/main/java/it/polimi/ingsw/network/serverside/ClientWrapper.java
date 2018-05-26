package it.polimi.ingsw.network.serverside;

import it.polimi.ingsw.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class ClientWrapper {

    public Player player;
    ObservableList<ClientWrapper> clients = FXCollections.observableArrayList();
    ServerInterface server;


    public abstract String loginResponse(boolean response);
    public abstract void disconnect();

    public void updatePlayersInfo(ClientWrapper c) {
        clients.add(c);

    }


    public ObservableList<ClientWrapper> getClients() {
        return clients;

    }


    public void setCurrentLogged(List<ClientWrapper> c) {
        clients.addAll(c);

    }

    public String getName() {
        return player.getName();
    }

}
