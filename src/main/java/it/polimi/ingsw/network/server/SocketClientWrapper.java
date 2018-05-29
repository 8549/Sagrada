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
            return "Login Accepted !";
        }
        return "Login Failed !";
    }

    @Override
    public void disconnect() {
        clients.remove(this);
    }

    @Override
    public void updatePlayersInfo(ClientWrapper c) {
        clients.add(c);


    }
}
