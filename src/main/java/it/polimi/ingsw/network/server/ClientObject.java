package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;

import java.util.List;

public abstract class ClientObject  {
    ServerInterface server;
    Player player;

    public abstract void login();
    public abstract void pushPlayers(List<Player> players);
    public abstract void pushLoggedPlayer(Player player);

    public Player getPlayer(){
        return this.player;

    }
}
