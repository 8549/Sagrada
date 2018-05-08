package it.polimi.ingsw.network;

import java.rmi.Remote;

public interface ServerInterface extends Remote {
    public void startGame();
    public void acceptConnection();
    public void notifyClients();

}
