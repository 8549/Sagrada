package it.polimi.ingsw.network;

import java.rmi.Naming;

//TODO: Implement GUI to choose RMI or SOCKET
public class RunClient {

    static String ip = "127.0.0.1";
    public static void main(String[] args) {
        ClientInterface client ;
        ServerInterface server;

        try {
            client = new RMIClient();
            server = (ServerInterface) Naming.lookup("rmi://"  + ip + "/sagrada");
        }catch (Exception e ){
            e.printStackTrace();
        }
    }


}
