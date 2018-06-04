package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;

import java.util.List;

public class SocketClientObject extends ClientObject {

    List<ClientObject> clients;

    public SocketClientObject(Player player, ServerInterface server){
        this.player = player;
        this.server = server;
    }
    @Override
    public void login() {

    }
}
