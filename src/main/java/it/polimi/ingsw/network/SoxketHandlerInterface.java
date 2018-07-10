package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.ClientObject;

public interface SoxketHandlerInterface  {
    public void log(String message);

    public boolean send(String type, String header, String s);


    public  ClientObject getClient();

    public  void setClient(ClientObject client);
}
