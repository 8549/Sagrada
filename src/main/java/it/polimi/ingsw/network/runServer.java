package it.polimi.ingsw.network;

import java.rmi.Naming;

public class runServer {
    private static final int DEFAULT_PORT = 3131;

    public static void main(String[] args) {

        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(DEFAULT_PORT);

            ServerInterface server = new RMIServer();
            Naming.rebind("rmi://127.0.0.1:" + DEFAULT_PORT + "/sagrada", server);
            System.out.println("[System] Server is ready.");
        }catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }
}
