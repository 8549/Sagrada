package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;

import java.util.logging.SocketHandler;

public class SocketClientWrapper extends ClientWrapper {

    SocketHandler socketHandler;
    SocketServer server;

    public SocketClientWrapper(String data, SocketServer s){
        this.player = new Player(data);
        this.server = server;
    }

    @Override
    public String loginResponse(boolean response) {
        if (response){
            return "Login Acceppted !";
        }
        return "Login Failed !";
    }

    @Override
    public void disconnect() {
        clients.remove(this);
        server.showClients();
    }
}
