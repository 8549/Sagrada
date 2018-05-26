package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;

import java.util.logging.SocketHandler;

public class SocketClientWrapper extends ClientWrapper {

    SocketHandler socketHandler;

    public SocketClientWrapper(String data){
        this.player = new Player(data);
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
    }
}
