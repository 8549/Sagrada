package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStatus;
import it.polimi.ingsw.network.client.RMIClient;
import it.polimi.ingsw.network.client.RMIClientInterface;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RMIServer  implements RMIServerInterface {
    List<ClientObject> users ;
    MainServer server;
    RMIServerInterface stub;
    List<RMIServerInterface> objs= new ArrayList<>();

    public RMIServer(List<ClientObject> users, MainServer server) throws RemoteException {
        this.users = users;
        this.server = server;
    }

    @Override
    public void start(String[] args) throws RemoteException {
        //RMI SERVER
       /* OptionParser parser = new OptionParser();
        parser.accepts("p").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_RMI_PORT);
        OptionSet set = parser.parse(args);

        int port = (int) set.valueOf("p");*/


        try {

            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(this, 0);

            System.out.println("IP addres of server: " + InetAddress.getLocalHost().getHostAddress());
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());


            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("RMIServerInterface", stub);

            System.out.println("[DEBUG] RMI server is ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }


    }

    public RMIServerInterface getEndPoint() throws RemoteException {
        RMIServerInterface obj = new RMIServer(users, server);
        RMIServerInterface  stub = (RMIServerInterface) UnicastRemoteObject.exportObject(obj, 0);
        objs.add(stub);
        return stub;
    }

    public void pingClient(RMIClientObjectInterface client){
       Ping ping = new Ping(client);
       ping.run();
    }

    @Override
    public void login(Player p, RMIClientInterface c) throws RemoteException {
        RMIClientObjectInterface client = new RMIClientObject(p, c);
        RMIClientObjectInterface clientObject = (RMIClientObjectInterface) UnicastRemoteObject.exportObject(client,0);
        PlayerStatus result= server.addClient(clientObject);

        if(result.equals(PlayerStatus.ACTIVE)){
            clientObject.answerLogin(true);
            server.addAlreadyLoogedPlayers(clientObject);
            server.addLoggedPlayer(clientObject.getPlayer());
            pingClient(clientObject);

            server.checkTimer();
        }else if(result.equals(PlayerStatus.ALREADYINGAME) || result.equals(PlayerStatus.NOTINGAME)){
            clientObject.answerLogin(false);

        } else if(result.equals(PlayerStatus.RECONNECTED)){
            clientObject.reconnection();
        }


    }

    @Override
    public void patternCardValidation(String patternName, RMIClientInterface c) throws RemoteException {
        ClientObject client=null;
        for(ClientObject clients: users){
            try {
                if(clients.getPlayer().getName().equals(c.getName())){
                    client = clients;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        server.setPlayerChoice(client, patternName);
    }

    @Override
    public void validateMove(Die d, int row, int column, Player p) throws RemoteException {
        server.handleMove(d, row, column, p);
    }

    @Override
    public void passTurn(String name) throws RemoteException {
        server.passTurn(name);
    }

    @Override
    public void requestTool(String name, String tool) throws RemoteException {
        server.useTool(name, tool);
    }

    @Override
    public RMIServerInterface getNewStub() throws RemoteException {
        return getEndPoint();
    }

    @Override
    public void setDieFromWP(int row, int column) throws RemoteException {
        server.getActiveToolCardHandler().setDieFromWindowPattern(row, column);
    }

    @Override
    public void setDieFromDP(Die d) throws RemoteException {
        server.getActiveToolCardHandler().setDieFromDraftPool(d);
    }

    @Override
    public void setDieFromRT(Die d, int round) throws RemoteException {
        server.getActiveToolCardHandler().setDieFromRoundTrack(d, round);
    }

    @Override
    public void setDecrease(boolean choice) throws RemoteException {
        server.getActiveToolCardHandler().setDecreaseChoice(choice);
    }

    @Override
    public void setPlacementChoice(boolean choice) throws RemoteException {
        server.getActiveToolCardHandler().setIfPlace(choice);
    }

    @Override
    public void setNumberDiceChoice(boolean choice) throws RemoteException {
        server.getActiveToolCardHandler().setMovementChoice(choice);
    }

    @Override
    public void setValue(int value) throws RemoteException {
        server.getActiveToolCardHandler().chosenValue(value);
    }


    @Override
    public void setNewCoordinates(int row, int column) throws RemoteException {
        server.getActiveToolCardHandler().setNewCoordinatesChoice(row, column);
    }

    @Override
    public void setTwoDice(int row1, int col1, int row2, int col2) throws RemoteException {
        server.getActiveToolCardHandler().setTwoDiceFromWindowPattern(row1, col1, row2, col2);
    }

    @Override
    public void setTwoNewCoordinates(int row1, int col1, int row2, int col2) throws RemoteException {
        server.getActiveToolCardHandler().setTwoNewCoordinatesChoice(row1, col1, row2, col2);
    }

    private class Ping implements Runnable{
        RMIClientObjectInterface client;

        public Ping(RMIClientObjectInterface client){
            this.client=client;
        }

        @Override
        public void run() {
            final boolean[] isTimerRunning = {false};
            final boolean[] isAlive = {false};
            Timer timer1 = new Timer();
            Timer timer2 = new Timer();

            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(!isTimerRunning[0]) {
                            isTimerRunning[0] = true;
                            //System.out.println("[DEBUG] Ping Sent");
                            isAlive[0] = client.ping();

                        }else{
                            isAlive[0] = false;
                            //System.out.println("[DEBUG] Ping sent");
                            timer2.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (!isAlive[0]) {
                                        System.out.println("[DEBUG] Client disconnected");
                                        this.cancel();
                                        timer1.cancel();
                                        isTimerRunning[0] = false;
                                        server.disconnect(client);

                                    }
                                }
                            }, 8*1000);
                            isAlive[0] = client.ping();

                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        isTimerRunning[0] = false;
                        timer1.cancel();
                        timer2.cancel();
                        server.disconnect(client);
                    }
                }
            }, 4 * 1000, 5 * 1000 );
        }
    }

}
