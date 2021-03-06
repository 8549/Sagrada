package it.polimi.ingsw.network.server;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.RMI.RMIServer;
import it.polimi.ingsw.network.server.RMI.RMIServerInterface;
import it.polimi.ingsw.network.server.socket.SocketServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainServer {
    public static final int DEFAULT_SOCKET_PORT= 3130;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    private int connectionTimeout;
    private int turnTimeout;

    public int getTurnTimeout() {
        return turnTimeout;
    }
    private RMIServerInterface rmiServer;  //TODO: check if it is really right to create the common server interface
    private ServerInterface socketServer;
    private Timer timer;
    private boolean timerIsRunning=false;
    ServerState state = ServerState.WAITINGPLAYERS;
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
    public void start(String[] args){
        if (args.length > 1) {
            try {
                connectionTimeout = Integer.valueOf(args[0]);
                System.out.println("[DEBUG] Connection timer set to " + connectionTimeout);
            } catch (NumberFormatException e) {
                System.err.println("Couldn't parse args[0] from command line, using the default connection timeout (" + DEFAULT_CONNECTION_TIMEOUT + "ms)");
                connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
            }
            try {
                turnTimeout = Integer.valueOf(args[1]);
                System.out.println("[DEBUG] Turn timer set to " + turnTimeout);
            } catch (NumberFormatException e) {
                System.err.println("Couldn't parse args[1] from command line, using the default turn timer timeout (" + GameManager.DEFAULT_TURN_TIMEOUT + "ms)");
                turnTimeout = GameManager.DEFAULT_TURN_TIMEOUT;
            }
        } else if (args.length > 0) {
            try {
                connectionTimeout = Integer.valueOf(args[0]);
                turnTimeout = Integer.valueOf(args[0]);
                System.out.println("[DEBUG] Both connection and turn timers set to " + connectionTimeout);
            } catch (NumberFormatException e) {
                System.err.print("Couldn't parse args[0] from command line, using the default connection timeout (" + DEFAULT_CONNECTION_TIMEOUT + "ms)");
                System.err.print(" and turn timeout (" + GameManager.DEFAULT_TURN_TIMEOUT + "ms)\n");
                connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
                turnTimeout = GameManager.DEFAULT_TURN_TIMEOUT;
            }
        } else {
            System.out.println("[DEBUG] Using default timers (connection " + DEFAULT_CONNECTION_TIMEOUT + "ms, turn " + GameManager.DEFAULT_TURN_TIMEOUT + "ms)");
            connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
            turnTimeout = GameManager.DEFAULT_TURN_TIMEOUT;
        }

        //RMI Server
        rmiServer = new RMIServer(connectedClients, this);


        new Thread(){
            public void run(){
                try {
                    rmiServer.start();
                } catch (Exception e){
                    e.getMessage();
                }
            }
        }.start();


        //Socket server
        socketServer= new SocketServer(connectedClients, this);

        new Thread(){
            public void run(){
                try {

                    socketServer.start();

                }catch (IOException e){
                    e.getMessage();
                }
            }
        }.start();

    }


    public  PlayerStatus addClient(ClientObject client){
        if(state.equals(ServerState.GAMESTARTED)){
            for(Player p : gm.getPlayers()){
                try {
                    if(p.getName().equals(client.getPlayer().getName()) && p.getStatus().equals(PlayerStatus.DISCONNECTED)){
                        connectedClients.add(client);
                        inGameClients.add(client);
                        gm.reconnectPlayer(gm.getPlayerByName(client.getPlayer().getName()));
                        return PlayerStatus.RECONNECTED;
                    }
                } catch (IOException e) {
                    e.getMessage();
                }
            }
            return PlayerStatus.NOTINGAME;

        } else {
            if(connectedClients.size()==0 ){
                connectedClients.add(client);
                return PlayerStatus.ACTIVE;

            }else{

                for (ClientObject clients : connectedClients){
                    try {
                        if (clients.getPlayer().getName().equals(client.getPlayer().getName())){
                            return PlayerStatus.ALREADYINGAME;
                        }
                    } catch (IOException e) {
                        e.getMessage();
                    }

                }
                connectedClients.add(client);
                return PlayerStatus.ACTIVE;
            }
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
                                if(state.equals(ServerState.WAITINGPLAYERS)) {
                                    new Thread(){
                                        public void run(){
                                            initGame(getPlayersFromClients(connectedClients));
                                            state = ServerState.GAMESTARTED;
                                        }
                                    }.start();

                                }
                            }
                        }catch (Exception e){
                            System.out.println("Exception inside timer!!!!!!!!!!!!!!!!!!!!!!!");
                            e.getMessage();
                        }
                    }
                }, connectionTimeout);

                System.out.println("Timer has started!!" );

            }else if(connectedClients.size()==4){
                if (timerIsRunning) {
                    timer.cancel();
                    timerIsRunning = false;
                    System.out.println("Timer stopped");
                }
                System.out.println("Let the game begin !");
                if(state.equals(ServerState.WAITINGPLAYERS)) {
                    initGame(getPlayersFromClients(connectedClients));
                    state = ServerState.GAMESTARTED;
                }


            } else if(connectedClients.size()>4){
                System.out.println("Too many users !");
                return false;
            }
        }
        return true;

    }


    public void disconnect(ClientObject client){
        try {
            if(gm.getPlayerByName(client.getPlayer().getName()).getStatus().equals(PlayerStatus.ACTIVE)){
                if(state.equals(ServerState.WAITINGPLAYERS)){
                    if(connectedClients.size()>1) {
                        for (ClientObject c : connectedClients) {
                            try {
                                if (!c.getPlayer().getName().equals(client.getPlayer().getName())) {
                                    c.notifyPlayerDisconnection(client.getPlayer());
                                }
                            } catch (IOException e) {
                                e.getMessage();
                            }
                        }
                    }
                    connectedClients.remove(client);
                    checkTimer();

                }else if (state.equals(ServerState.GAMESTARTED)){
                    try {
                        connectedClients.remove(getClientByName(client.getPlayer().getName()));
                        inGameClients.remove(getClientByName(client.getPlayer().getName()));
                        gm.disconnectPlayer(client.getPlayer());
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
            }

        } catch (IOException e) {
            e.getMessage();
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
                e.getMessage();
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
                e.getMessage();
            }
        }

    }

    public  void addAlreadyLoogedPlayers(ClientObject client){
        if (connectedClients.size()>0) {
            try {
                client.pushPlayers(getPlayersFromClients(connectedClients));
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public  void gameStartedProcedures(List<Player> players, int timeoutMove){
        List<Player> p = new ArrayList<>(players);
        inGameClients.addAll(connectedClients);
        for (ClientObject c : inGameClients){
            try {
                c.notifyGameStarted(p, timeoutMove);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public  void gameStartedProceduresAfterReconnect(List<Player> players, int timeoutMove, Player player){
        List<Player> p = new ArrayList<>(players);
        inGameClients.addAll(connectedClients); //todo:check for lobby
        ClientObject c = getClientByName(player.getName());
            try {
                c.notifyGameStarted(p, timeoutMove);
            } catch (IOException e) {
                e.getMessage();
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
                e.getMessage();
            }
        }
        return;
    }


    public  void setPlayerChoice(ClientObject client, String name){
        try {
            client.pushPatternCardResponse(name);
            gm.completePlayerSetup(client.getPlayer(), name);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void initPatterAfterReconnection(ClientObject client, String name){
        try {
            client.pushPatternCardResponse(name);
        } catch (IOException e) {
            e.getMessage();
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
                    e.getMessage();
                }
            }
            System.out.println("Pushing opponents: " + thinPlayers.toString());
            try {
                client1.pushOpponentsInit(thinPlayers);
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }

    public  void initPlayersData(List<Player> players, Player player){

        ClientObject client1 = getClientByName(player.getName());

            List<Player> thinPlayers = new ArrayList<>();
            for(Player client2 : players){
                try {
                    if (!client1.getPlayer().getName().equals(client2.getName())){
                        thinPlayers.add(getOpponentVisibleFromClient(client2));

                    }
                } catch (IOException e) {
                    e.getMessage();
                }
            }
            System.out.println("Pushing opponents: " + thinPlayers.toString());
            try {
                client1.pushOpponentsInit(thinPlayers);
            } catch (IOException e) {
                e.getMessage();
            }

    }


    public  Player getOpponentVisibleFromClient(Player p ){
        Player player = new Player(p.getName());
        player.getPlayerWindow().setWindowPattern(p.getPlayerWindow().getWindowPattern());
        player.setPrivateObjectiveCard(null);
        player.setInitialTokens();
        return player;
    }

    public  void setPublicObj(PublicObjectiveCard[] publicObj){
        for(ClientObject c : inGameClients){
            try {
                c.pushPublicObj(publicObj);
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }

    public  void setPublicObj(PublicObjectiveCard[] publicObj, Player p){
        try {
            getClientByName(p.getName()).pushPublicObj(publicObj);
        } catch (IOException e) {
            e.getMessage();
        }

    }

    public  void setPrivateObj(Player p){
        try {
            getClientByName(p.getName()).setPrivObj(p.getPrivateObjectiveCard().getName(), getPlayersFromClients(inGameClients));
        } catch (IOException e) {
            e.getMessage();
        }

    }

    public  void setDraft(List<Die> draft){
        for (ClientObject c: inGameClients){
            try {
                c.pushDraft(draft);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public  void notifyBeginTurn(Player p, int round, int turn){
        for (ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName()) && gm.getPlayerByName(c.getPlayer().getName()).getStatus().equals(PlayerStatus.ACTIVE) ) {
                c.notifyTurn(p, round, turn);}
            } catch (IOException e) {
                e.getMessage();
            }
        }
        try {
            getClientByName(p.getName()).notifyTurn(p, round, turn);
        } catch (IOException e) {
            e.getMessage();
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
                e.getMessage();
            }
        }
    }

    public void pushTools(List<ToolCard> toolCards, Player p){
        List<String> tools = new ArrayList<>();
        for(ToolCard tool : toolCards){
            tools.add(tool.getName());
        }

        try {
            getClientByName(p.getName()).pushToolCards(tools);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public  void notifyPlacementResponse(boolean response, Player p, Die d, int row, int column){
        for(ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName()) && gm.getPlayerByName(c.getPlayer().getName()).getStatus().equals(PlayerStatus.ACTIVE) ){
                    c.notifyMoveResponse(response, p.getName(), d, row, column);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

        try {
            if(gm.getPlayerByName(p.getName()).getStatus().equals(PlayerStatus.ACTIVE) ){
                getClientByName(p.getName()).notifyMoveResponse(response, p.getName(),d , row, column);

            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public  void handleMove(Die d, int row, int column, Player p ){
        gm.processMove(d, row, column, p);
    }

    public void moveTimeOut(Player p){
        for(ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName())){
                    c.notifyEndTimeOut(p);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

        try {
            if(p.getStatus().equals(PlayerStatus.ACTIVE)) {
                getClientByName(p.getName()).notifyEndTimeOut(p);
            }
        } catch (IOException e) {
            e.getMessage();
        }

    }

    public void notifyEndTurn(Player p){
        for(ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName())){
                    c.notifyEndTurn(p);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

        try {
            if(p.getStatus().equals(PlayerStatus.ACTIVE)){
                getClientByName(p.getName()).notifyEndTurn(p);
            }
        } catch (IOException e) {
            e.getMessage();
        }

    }
    public void notifyEndRound( List<Die> dice){
        for(ClientObject c: inGameClients){
            try {
                c.notifyEndRound(dice);
            } catch (IOException e) {
                e.getMessage();
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
                e.getMessage();
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


    public void notifyPlayerIfToolCardWorked(boolean toolCardWorked) {
        for(ClientObject c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(gm.getCurrentPlayer().getName()))
                    c.notifyToolUsed(toolCardWorked, gm.getCurrentPlayer().getName());
            } catch (IOException e) {
                e.getMessage();
            }
        }
        try {
            getClientByName(gm.getCurrentPlayer().getName()).notifyToolUsed(toolCardWorked, gm.getCurrentPlayer().getName());
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void notifyMoveNotAvailable() {
        try {
            getClientByName(gm.getCurrentPlayer().getName()).notifyMoveNotAvailable();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void askPlayerForNextMove() {
        try {
            getClientByName(gm.getCurrentPlayer().getName()).nextMove();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void notifyPlayerToolCardAlreadyUsed() {
    }

    public void notifyScore(List<Player> playersWithPoints) {
        for(ClientObject c : inGameClients){
            try {
                c.pushFinalScore(playersWithPoints);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public void startUpdateModel(ClientObject client){
        gm.updateModel(client);
    }

    public void notifyFinishUpdate(ClientObject c, Player p) {
        try {
            c.notifyFinishUpdate(p.getName());
        } catch (IOException e) {
            e.getMessage();
        }

    }
}
