package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.server.MainServer;


public class RunServer {


    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.start(args);

    }

}
