package it.polimi.ingsw.network.server;

import com.sun.security.ntlm.Client;
import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class MainServer {
    public static final int DEFAULT_RMI_PORT = 1234;
    public static final int DEFAULT_SOCKET_PORT= 3130;
    public static final int CONNECTION_TIMEOUT = 5;
    private RMIServerInterface rmiServer;  //TODO: check if it is really right to create the common server interface
    private ServerInterface socketServer;
    private Timer timer;
    private boolean timerIsRunning=false;
    private boolean isGameStarted = false;

    private List<ClientObject> connectedClients = new ArrayList<>();
    private List<ClientObject> inGameClients = new ArrayList<>();
    private GameManager gm;

    /*


    This class must unify all the actions that come from RMIServer and SocketServer and let the ServerHandler handle the actions

    RMIServer and SocketServer must handle just the comunication of a certain message, but both processInput in SocketClient and the methods in RMIServer refer to this class
    to act
    */

    public MainServer(String[] args){

        // RMI server
        try {
            rmiServer = new RMIServer(connectedClients, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //Socket server
        socketServer= new SocketServer(connectedClients, this);

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

    }


    public synchronized boolean addClient(ClientObject client){
        if(isGameStarted){
            return false;
        }
        if(connectedClients == null ){
            connectedClients.add(client);
            return true;

        }else{

            for (ClientObject clients : connectedClients){
                if (clients.getPlayer().getName().equals(client.getPlayer().getName())){
                    return false;
                }

            }

            connectedClients.add(client);
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
                            if (connectedClients.size() >= 2) {
                                System.out.println("Let the game begin !");
                                timerIsRunning = false;
                                if(!isGameStarted) {
                                    initGame(getPlayersFromClients(connectedClients));
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
                    initGame(getPlayersFromClients(connectedClients));
                    isGameStarted = true;
                }


            } else if(connectedClients.size()>4){
                System.out.println("Too many users !");
                return false;
            }
        }
        return true;

    }


    public void disconnect(ClientObject client){
            if(!isGameStarted){
                if(connectedClients.size()>1) {
                    for (ClientObject c : connectedClients) {
                        if (!c.getPlayer().getName().equals(client.getPlayer().getName())) {
                            c.notifyPlayerDisconnection(client.getPlayer());
                        }
                    }
                }
                connectedClients.remove(client);
                checkTimer();
            }else{
                //CODE TO HANDLE DISCONNECTION MEANWHILE THE GAME
            }
    }

    public void initGame(List<Player> players){
        //Code to init Game manager
        gm = new GameManager(this, players);

    }

    public List<Player> getPlayersFromClients(List<ClientObject> clients){
        List<Player> players= new ArrayList<>();

        for (ClientObject c : clients){
            players.add(c.getPlayer());
        }
        return players;
    }

    public void addLoggedPlayer(Player p) {
        for(ClientObject c : connectedClients){
            if (!c.getPlayer().getName().equals(p.getName())){
                c.pushLoggedPlayer(p);
            }
        }

    }

    public void addAlreadyLoogedPlayers(ClientObject client){
        if (connectedClients.size()>0) {
            client.pushPlayers(getPlayersFromClients(connectedClients));
        }
    }

    public void gameStartedProcedures(List<Player> players){
        inGameClients.addAll(connectedClients);
        for (ClientObject c : inGameClients){
            c.notifyGameStarted(players);
        }
    }

    public void choosePatternCard(List<PatternCard> patternCards, Player player){
        for(ClientObject c : inGameClients ){
            if (c.getPlayer().getName().equals(player.getName())){
                c.requestPatternCardChoice(patternCards);
                break;
            }
        }
    }

    //TODO

    public void chooseDie(List<Die> dice){}

    public Die setDie(){
        return null;
    }

    public void choosePlacement(){
        //notify client to choose die and coordinates
    }

    public void setChoosePlacement(){

    }
    public void notifyPlayerChangeTurn(){}

    public void notifyCorrectMovement(){}

    public void notifyIncorrectMovement(){}


    public void setPlayerChoice(ClientObject client, String name){
        //TODO: check correct pattern card
        client.pushPatternCardResponse(name);
        gm.completePlayerSetup(client.getPlayer(), name);

    }

    public void initPlayersData(){
        for(ClientObject client1 : inGameClients){
            List<Player> thinPlayers = new ArrayList<>();
            for(ClientObject client2 : inGameClients){
                if (!client1.getPlayer().getName().equals(client2.getPlayer().getName())){
                    thinPlayers.add(getOpponentVisibleFromClient(client2.getPlayer()));

                }
            }
            System.out.println("Pushing opponents: " + thinPlayers.toString());
            client1.pushOpponentsInit(thinPlayers);
        }

    }

    public Player getOpponentVisibleFromClient(Player p ){
        Player player = new Player(p.getName());
        player.getPlayerWindow().setWindowPattern(p.getPlayerWindow().getWindowPattern());
        player.setPrivateObjectiveCard(null);
        return player;
    }

    public void setPublicObj(ObjCard[] publicObj){
        for(ClientObject c : inGameClients){
            c.pushPublicObj(publicObj);
        }

    }

    public void setPrivateObj(Player p, ObjCard privateObjectiveCard){
        for (ClientObject c : inGameClients) {
            if (c.getPlayer().getName().equals(p.getName())) {
                System.out.println("SETTING private " + privateObjectiveCard.getName() + "  to " + p.getName());
                c.setPrivObj(privateObjectiveCard, getPlayersFromClients(inGameClients));
            }
        }
    }

    public void setDraft(List<Die> draft){
        for (ClientObject c: inGameClients){
            c.pushDraft(draft);
        }
    }

    public void notifyBeginTurn(Player p ){
        for (ClientObject c : inGameClients){
            c.notifyTurn(p);

        }
    }

}
