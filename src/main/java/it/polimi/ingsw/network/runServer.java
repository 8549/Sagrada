package it.polimi.ingsw.network;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.network.server.ClientWrapper;
import it.polimi.ingsw.network.server.RMIServer;
import it.polimi.ingsw.network.server.SocketServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.RemoteException;


public class runServer {
    public static final int DEFAULT_RMI_PORT = 1234;
    public static final int DEFAULT_SOCKET_PORT= 3130;
    static ObservableList<ClientWrapper> users = FXCollections.observableArrayList();
    static ObservableList<ClientWrapper> lobby = FXCollections.observableArrayList();

    GameManager gm;


    public static void main(String[] args) {

        try {
            // RMI server
            RMIServer rmiServer = new RMIServer(users, lobby);

            //Socket server
            SocketServer socketServer= new SocketServer(users, lobby);

            new Thread(){
                public void run(){
                    try {

                        socketServer.start(args);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

            new Thread(){
                public void run(){
                    try {

                        rmiServer.start(args);

                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (RemoteException e) {
            System.err.println("Server failed due to RMI problem: " + e);
        } catch (IOException e){
            System.err.println("Server failed due to Socket problem: " + e);

        }

        while (users.size()<4 ) {}
        System.out.println("Users----- > " + users.toString());
    }

}
