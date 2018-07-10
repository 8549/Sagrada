package it.polimi.ingsw.network;


import java.io.IOException;

public interface SocketInterface {


    void processInput(String type, String header, String data, SoxketHandlerInterface s) throws IOException;

}
