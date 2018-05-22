package it.polimi.ingsw.network;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;

public class runServer {
    public static final int DEFAULT_RMI_PORT = 3131;
    public static final int DEFAULT_SOCKET_PORT= 3130;

    public static void main(String[] args) {

        try {

            new Thread(){
                public void run(){
                    try {
                        // RMI server
                        RMIServer rmiServer = new RMIServer();
                        rmiServer.start(args);

                    }catch (Exception e){

                    }
                }
            }.start();

            new Thread(){
                public void run(){
                    try {
                        //Socket server
                        SocketServer socketServer= new SocketServer();
                        socketServer.start(args);

                    }catch (Exception e){

                    }
                }
            }.start();



                System.out.println("[System] RMI Server is listening on port " + DEFAULT_RMI_PORT);
                System.out.println("[System] Socket server is listening on port " + DEFAULT_SOCKET_PORT);



        }catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }

}
