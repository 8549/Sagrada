package it.polimi.ingsw;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.network.server.MainServer;
import it.polimi.ingsw.network.server.RMIServer;
import it.polimi.ingsw.network.server.SocketServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.RemoteException;


public class RunServer {


    public static void main(String[] args) {
        MainServer mainServer = new MainServer(args);

    }

}
