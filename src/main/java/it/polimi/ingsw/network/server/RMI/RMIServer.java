package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStatus;
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
    List<RMIServerInterface> objs= new ArrayList<>();

    public RMIServer(List<ClientObject> users, MainServer server) {
        this.users = users;
        this.server = server;
    }

    @Override
    public void start() {

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
            e.getMessage();
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
       ping.start();
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
            clientObject.answerLogin(true);
            clientObject.reconnection();

            server.startUpdateModel(client);
        }


    }

    @Override
    public void patternCardValidation(String patternName, RMIClientInterface c) {
        ClientObject client=null;
        for(ClientObject clients: users){
            try {
                if(clients.getPlayer().getName().equals(c.getName())){
                    client = clients;
                }
            } catch (IOException e) {
                e.getMessage();
            }

        }
        server.setPlayerChoice(client, patternName);
    }

    @Override
    public void validateMove(Die d, int row, int column, Player p) {
        server.handleMove(d, row, column, p);
    }

    @Override
    public void passTurn(String name) {
        server.passTurn(name);
    }

    @Override
    public void requestTool(String name, String tool) {
        server.useTool(name, tool);
    }

    @Override
    public void setDieFromWP(int row, int column) {
        server.getActiveToolCardHandler().setDieFromWindowPattern(row, column);
    }

    @Override
    public void setDieFromDP(Die d) {
        server.getActiveToolCardHandler().setDieFromDraftPool(d);
    }

    @Override
    public void setDieFromRT(Die d, int round) {
        server.getActiveToolCardHandler().setDieFromRoundTrack(d, round);
    }

    @Override
    public void setDecrease(boolean choice) {
        server.getActiveToolCardHandler().setDecreaseChoice(choice);
    }

    @Override
    public void setPlacementChoice(boolean choice) {
        server.getActiveToolCardHandler().setIfPlace(choice);
    }

    @Override
    public void setNumberDiceChoice(boolean choice) {
        server.getActiveToolCardHandler().setMovementChoice(choice);
    }

    @Override
    public void setValue(int value) {
        server.getActiveToolCardHandler().chosenValue(value);
    }


    @Override
    public void setNewCoordinates(int row, int column) {
        server.getActiveToolCardHandler().setNewCoordinatesChoice(row, column);
    }

    @Override
    public void setTwoDice(int row1, int col1, int row2, int col2) {
        server.getActiveToolCardHandler().setTwoDiceFromWindowPattern(row1, col1, row2, col2);
    }

    @Override
    public void setTwoNewCoordinates(int row1, int col1, int row2, int col2) {
        server.getActiveToolCardHandler().setTwoNewCoordinatesChoice(row1, col1, row2, col2);
    }

    @Override
    public boolean clientPing() {
        return true;
    }

    private class Ping extends Thread{
        private RMIClientObjectInterface client;

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
                            System.out.println("[DEBUG] RMI Ping Sent");
                            isAlive[0] = client.ping();

                        }else{
                            isAlive[0] = false;
                            System.out.println("[DEBUG] RMI Ping sent");
                            timer2.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (!isAlive[0]) {
                                        try {
                                            System.out.println("[DEBUG] Client " + client.getPlayer().getName() + " disconnected");
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                        this.cancel();
                                        timer1.cancel();
                                        isTimerRunning[0] = false;
                                        server.disconnect(client);

                                    }
                                }
                            }, 5 * 1000);
                            isAlive[0] = client.ping();

                        }
                    } catch (RemoteException e) {
                        e.getMessage();
                        isTimerRunning[0] = false;
                        timer1.cancel();
                        timer2.cancel();
                        server.disconnect(client);
                    }
                }
            },  0, 20 * 1000 );
        }
    }

}
