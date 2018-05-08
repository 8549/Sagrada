package it.polimi.ingsw;

import java.rmi.Remote;

public interface ServerInterface extends Remote {
    public void startGame();
    public void acceptConnection();
    public void notifyClients();

}
