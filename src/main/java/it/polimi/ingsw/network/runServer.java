package it.polimi.ingsw.network;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.rmi.Naming;

public class runServer {
    private static final int DEFAULT_PORT = 3131;

    public static void main(String[] args) {

        try {

            OptionParser parser = new OptionParser();
            parser.accepts("p").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
            OptionSet set = parser.parse(args);

            int port = (int) set.valueOf("p");
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(port);

            ServerInterface server = new RMIServer();
            Naming.rebind("rmi://127.0.0.1:" + port + "/sagrada", server);
            System.out.println("[System] Server is ready on port " + port);
        }catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }
}
