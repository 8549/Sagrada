package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainServer {
    public static final int DEFAULT_RMI_PORT = 1234;
    public static final int DEFAULT_SOCKET_PORT= 3130;
    public static final int CONNECTION_TIMEOUT = 200;
    private ServerInterface rmiServer;
    private ServerInterface socketServer;
    Timer timer;
    private boolean timerIsRunning=false;
    private boolean isGameStarted = false;

    private List<ClientObject> connectedClients = new ArrayList<>();
    /*
    TODO: this class must handle the different requests from RMI and Socket, so it must use ClientWrapper and instantiate different ServerHandler objects for every
    TODO: different room of game

    This class must unify all the actions that come from RMIServer and SocketServer and let the ServerHandler handle the actions

    RMIServer and SocketServer must handle just the comunication of a certain message, but both processInput in SocketClient and the methods in RMIServer refer to this class
    to act
    */

    public MainServer(){

        // RMI server
        //rmiServer = new RMIServer(connectedClients, this);

        //Socket server
        socketServer= new SocketServer(connectedClients, this);

        new Thread(){
            public void run(){
                try {

                    socketServer.start();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

            /*new Thread(){
                public void run(){
                    try {

                        rmiServer.start(args);

                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }.start();*/

    }


    public synchronized boolean addClient(ClientObject client){
        if(connectedClients == null){
            connectedClients.add(client);
            checkTimer();
            return true;

        }else{
            for (ClientObject clients : connectedClients){
                if (clients.getPlayer().getName().equals(client.getPlayer().getName())){
                    return false;
                }

            }
            connectedClients.add(client);
            checkTimer();
            return true;
        }

    }

    public  boolean checkTimer() {
        if (connectedClients.size()<2){
            System.out.println("Waiting for more players . . .");
            if (timerIsRunning) {
                timer.cancel();
                timerIsRunning = false;
                System.out.println("Timer stopped");
            }
        }else {
            if (connectedClients.size()==2){
                timerIsRunning = true;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            //showClients();
                            System.out.println("Time to join the game is out !");
                            if (connectedClients.size() > 2) {
                                System.out.println("Let the game begin !");
                                timerIsRunning = false;
                                if(!isGameStarted) {
                                    //initGame(getPlayersFromClients(connectedClients));
                                    isGameStarted = true;
                                }
                            }
                        }catch (Exception e){
                            System.out.println("Exception inside timer!!!!!!!!!!!!!!!!!!!!!!!");
                            e.printStackTrace();
                        }
                    }
                }, CONNECTION_TIMEOUT*1000);

                System.out.println("Timer has started!!" );

            }else if(connectedClients.size()==4){
                if (timerIsRunning) {
                    timer.cancel();
                    timerIsRunning = false;
                    System.out.println("Timer stopped");
                }
                System.out.println("Let the game begin !");
                if(!isGameStarted) {
                    //initGame(getPlayersFromClients(connectedClients));
                    isGameStarted = true;
                }


            } else if(connectedClients.size()>4){
                System.out.println("Too many users !");
                return false;
            }
        }
        return true;

    }


public void disconnect(ClientObject c){
        if(!isGameStarted){
            connectedClients.remove(c);
            checkTimer();
        }else{
            //CODE TO HANDLE DISCONNECTION MEANWHILE THE GAME
        }
}


}
