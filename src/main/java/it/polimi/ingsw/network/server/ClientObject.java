package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;

public abstract class ClientObject  {
    ServerInterface server;
    Player player;

    public abstract void login();

    public Player getPlayer(){
        return this.player;

    }
}
