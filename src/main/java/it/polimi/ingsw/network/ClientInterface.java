package it.polimi.ingsw.network;

import java.rmi.Remote;

public interface ClientInterface extends Remote {
    public void connect();
    public void pushData();
    public void updatePlayersInfo();

}
