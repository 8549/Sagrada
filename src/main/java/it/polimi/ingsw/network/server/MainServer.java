package it.polimi.ingsw.network.server;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.RMI.RMIServer;
import it.polimi.ingsw.network.server.RMI.RMIServerInterface;
import it.polimi.ingsw.network.server.socket.SocketServer;

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
    private List<ToolCardHandler> toolCardHandlers = new ArrayList<>();


    /** MainServer handles the two different type of connections
     * (RMI and Socket), and unify them to communicate with GameManager
     *
     * @param args are the parameter from command line
     *
     */
    public MainServer(String[] args){

        //RMI Server
        try {
            rmiServer = new RMIServer(connectedClients, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        new Thread(){
            public void run(){
                try {
                    rmiServer.start(args);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();


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



    }


    public  boolean addClient(ClientObject client){
        if(isGameStarted){
            return false;
        }
        if(connectedClients == null ){
            connectedClients.add(client);
            return true;

        }else{

            for (ClientObject clients : connectedClients){
                try {
                    if (clients.getPlayer().getName().equals(client.getPlayer().getName())){
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
                                    new Thread(){
                                        public void run(){
                                            initGame(getPlayersFromClients(connectedClients));
                                            isGameStarted = true;
                                        }
                                    }.start();

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


    public  void disconnect(ClientObject client){
            if(!isGameStarted){
                if(connectedClients.size()>1) {
                    for (ClientObject c : connectedClients) {
                        try {
                            if (!c.getPlayer().getName().equals(client.getPlayer().getName())) {
                                c.notifyPlayerDisconnection(client.getPlayer());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                connectedClients.remove(client);
                checkTimer();
            }else{
                try {
                    gm.disconnectPlayer(client.getPlayer());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public  void initGame(List<Player> players){
        //Code to init Game manager
        gm = new GameManager(this, players);
        gm.init();

    }

    public  List<Player> getPlayersFromClients(List<ClientObject> clients){
        List<Player> players= new ArrayList<>();

        for (ClientObject c : clients){
            try {
                players.add(c.getPlayer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    public  void addLoggedPlayer(Player p) {
        for(ClientObject c : connectedClients){
            try {
                if (!c.getPlayer().getName().equals(p.getName())){
                    c.pushLoggedPlayer(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public  void addAlreadyLoogedPlayers(ClientObject client){
        if (connectedClients.size()>0) {
            try {
                client.pushPlayers(getPlayersFromClients(connectedClients));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void gameStartedProcedures(List<Player> players, int timeoutMove){
        List<Player> p = new ArrayList<>(players);
        inGameClients.addAll(connectedClients); //todo:check for lobby
        for (ClientObject c : inGameClients){
            try {
                c.notifyGameStarted(p, timeoutMove);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void choosePatternCard(List<PatternCard> patternCards, Player player){
        for(ClientObject c : inGameClients ){
            try {
                if (c.getPlayer().getName().equals(player.getName())){
                    c.requestPatternCardChoice(patternCards);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }


    public  void setPlayerChoice(ClientObject client, String name){
        //TODO: check correct pattern card
        try {
            while (gm==null){}
            client.pushPatternCardResponse(name);
            gm.completePlayerSetup(client.getPlayer(), name);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public  void initPlayersData(List<Player> players){
        for(ClientObject client1 : inGameClients){
            List<Player> thinPlayers = new ArrayList<>();
            for(Player client2 : players){
                try {
                    if (!client1.getPlayer().getName().equals(client2.getName())){
                        thinPlayers.add(getOpponentVisibleFromClient(client2));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Pushing opponents: " + thinPlayers.toString());
            try {
                client1.pushOpponentsInit(thinPlayers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public  Player getOpponentVisibleFromClient(Player p ){
        Player player = new Player(p.getName());
        player.getPlayerWindow().setWindowPattern(p.getPlayerWindow().getWindowPattern());
        player.setPrivateObjectiveCard(null);
        return player;
    }

    public  void setPublicObj(PublicObjectiveCard[] publicObj){
        for(ClientObject c : inGameClients){
            try {
                c.pushPublicObj(publicObj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public  void setPrivateObj(Player p){
        for (ClientObject c : inGameClients) {
            try {
                if (c.getPlayer().getName().equals(p.getName())) {
                    System.out.println("SETTING private " + p.getPrivateObjectiveCard().getName() + "  to " + p.getName());
                    c.setPrivObj(p.getPrivateObjectiveCard().getName(), getPlayersFromClients(inGameClients));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void setDraft(List<Die> draft){
        for (ClientObject c: inGameClients){
            try {
                c.pushDraft(draft);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void notifyBeginTurn(Player p, int round, int turn){
        for (ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName())) {
                c.notifyTurn(p, round, turn);}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            getClientByName(p.getName()).notifyTurn(p, round, turn);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pushTools(List<ToolCard> toolCards){
        List<String> tools = new ArrayList<>();
        for(ToolCard tool : toolCards){
            tools.add(tool.getName());
        }

        for (ClientObject c : inGameClients){
            try {
                c.pushToolCards(tools);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void notifyPlacementResponse(boolean response, Player p, Die d, int row, int column){
        for(ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName())){
                    c.notifyMoveResponse(response, p.getName(), d, row, column);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            getClientByName(p.getName()).notifyMoveResponse(response, p.getName(),d , row, column);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void handleMove(Die d, int row, int column, Player p ){
        gm.processMove(d, row, column, p);
    }

    public void moveTimeOut(){
        for(ClientObject c : inGameClients){
            try {
                if(c.getPlayer().getName().equals(gm.getCurrentPlayer().getName())){
                   c.notifyEndTimeOut();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyEndTurn(Player p){
        for(ClientObject c : inGameClients){
            try {
                c.notifyEndTurn(gm.getCurrentPlayer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void notifyEndRound( List<Die> dice){
        for(ClientObject c: inGameClients){
            try {
                c.notifyEndRound(dice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ClientObject> getInGameClients(){
        return this.inGameClients;
    }

    public ToolCardHandler getActiveToolCardHandler (){
        for(ToolCardHandler t : toolCardHandlers){
            if(t.isActive()){
                return t;
            }
        }
        return null;
    }

    public ClientObject getClientByName(String name){
        for(ClientObject c : inGameClients){
            try {
                if(c.getPlayer().getName().equals(name)){
                    return c;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void passTurn(String name){
        if(gm.getCurrentPlayer().getName().equals(name)){
            gm.endCurrentTurn();
        }
    }
    public void useTool(String name, String tool){
        gm.useTool(name, tool);
    }


    public void addToolCardHandler (ToolCardHandler toolCardHandlers){
        this.toolCardHandlers.add(toolCardHandlers);
    }

    public void notifyWinner(Optional<Player> winner) {
    }

    public void notifyLosers(List<Player> players) {
    }

    public void notifyPlayerIfToolCardWorked(boolean toolCardWorked) {
    }

    public void notifyMoveNotAvailable() {
        try {
            getClientByName(gm.getCurrentPlayer().getName()).notifyMoveNotAvailable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyToolCardResponse(boolean response){

    }

    public void askPlayerForNextMove() {
    }

    public void notifyPlayerToolCardAlreadyUsed() {
    }
}
