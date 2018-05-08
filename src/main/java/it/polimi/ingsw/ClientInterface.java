package it.polimi.ingsw;

import java.rmi.Remote;

public interface ClientInterface extends Remote {
    public void connect();
    public void pushData();
    public void updatePlayersInfo();

}
