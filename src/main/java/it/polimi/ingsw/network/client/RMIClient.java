package it.polimi.ingsw.network.client;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.RMI.RMIServerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RMIClient implements RMIClientInterface, Serializable {
    Player player;
    RMIServerInterface server;
    ClientHandler ch;

    public RMIClient(ClientHandler ch) {
        this.ch = ch;
    }


    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void login() throws RemoteException {
        Ping ping = new Ping(server);
        ping.run();
        server.login(player,(RMIClientInterface) UnicastRemoteObject.exportObject(this,0));
    }


    @Override
    public void connect(String serverAddress, int portNumber, String userName) throws RemoteException{
        player = new Player(userName);
        Registry registry = LocateRegistry.getRegistry(serverAddress);

        try {
            server = (RMIServerInterface) registry.lookup("RMIServerInterface");
//            System.out.println("[DEBUG] Client connected ");

        } catch (NotBoundException e) {
            e.getMessage();
        }
    }

    @Override
    public void validatePatternCard(WindowPattern w) throws RemoteException{
        server.patternCardValidation(w.getName(), this);
    }

    @Override
    public void requestPlacement(int number, String color, int row, int column) throws RemoteException{
        Die d = new Die(SagradaColor.valueOf(color));
        d.setNumber(number);
        server.validateMove(d,row, column, ch.getModel().getMyself());
    }

    @Override
    public void passTurn() throws IOException {
        server.passTurn(getName());
    }

    @Override
    public void loginResponse(boolean response) {
        if(response){
//             System.out.println("[DEBUG] Login succesful");
            ch.setPlayerToProxyModel(player.getName());
        }else{
//            System.out.println("[DEBUG] Login failed");
            ch.loginFailed();
        }


    }

    @Override
    public void addPlayersToProxy(List<Player> players) {
        ch.addPlayersToProxyModel(players);
        ch.loggedUsers();
    }

    @Override
    public void addPlayerToProxy(Player player) {
        ch.addPlayersToProxyModel(player);
    }

    @Override
    public void initPatternCardChoice(List<PatternCard> choices){
        ch.patternCardChooser(choices.get(0), choices.get(1));
    }

    @Override
    public void initGame(List<Player> p, int timeout ){
        ch.handleGameStarted(p, timeout);

    }

    @Override
    public void setPrivateObj(String name) {
        CardsDeck objDeckpriv = new CardsDeck("PrivateObjectiveCards.json", new TypeToken<List<PrivateObjectiveCard>>() {
        }.getType());
        CardsDeck blankDeck = new CardsDeck("BlankObjectiveCard.json", new TypeToken<List<PrivateObjectiveCard>>() {
        }.getType());
        PrivateObjectiveCard blankCard = (PrivateObjectiveCard) blankDeck.getRandomCard();

        ch.setPrivateObj(ch.getModel().getMyself().getName(), (PrivateObjectiveCard) objDeckpriv.getByName(name));

        for (Player p : ch.getModel().getPlayers()){
            ch.setPrivateObj(p.getName(), blankCard);
        }

    }

    @Override
    public void setPublicObj(List<PublicObjectiveCard> publicObjCards) {
        ch.setPublicObjCard(publicObjCards);
    }

    @Override
    public void setDraft(List<Die> draft) {
        ch.setDraftPool(draft);
    }

    @Override
    public void beginTurn(String name, int round, int turn) {
        ch.notifyTurnStarted(name, round, turn);
    }

    @Override
    public void patternCardResponse(String name) {
        ch.initPatternCard(name);
    }

    @Override
    public void initTools(List<String> names) {
        List<ToolCard> tools = new ArrayList<>();
        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
        }.getType());
        for(String name : names){
            tools.add((ToolCard) toolDeck.getByName(name));
        }
        ch.setTools(tools);
    }


    @Override
    public void updateOpponentsInfo(List<Player> players) {
        for(Player p1 : ch.getModel().getPlayers()){
            for(Player p2 : players){
                if (p1.getName().equals(p2.getName())){
                    ch.initPlayer(p2.getName(), p2.getPlayerWindow().getWindowPattern().getName());
                }
            }
        }
    }

    @Override
    public void moveResponse(String name, boolean response, Die d, int row, int column) {
        ch.handleMoveResponse(name, response, d, row, column);
    }

    @Override
    public void moveTimeOut(Player p) {
        ch.moveTimeIsOut(p);
    }

    @Override
    public void endCurrentTurn(String name) {
        ch.endTurn(name);
    }

    @Override
    public void endRound(List<Die> dice) {
        ch.endRound(dice);
    }

    @Override
    public void chooseDieFromWindowPattern() {
        ch.chooseDieFromWindowPattern();
    }

    @Override
    public void chooseDieFromDraftPool() {
        ch.chooseDieFromDraftPool();
    }

    @Override
    public void chooseDieFromRoundTrack() {
        ch.chooseDieFromRoundTrack();
    }

    @Override
    public void chooseIfDecrease() {
        ch.chooseIfDecrease();
    }

    @Override
    public void chooseIfPlaceDie(int number) {
        ch.chooseIfPlaceDie(number);
    }

    @Override
    public void chooseToMoveOneDie() {
        ch.chooseToMoveOneDie();
    }

    @Override
    public void setValue(String color) {
        ch.setValue(SagradaColor.valueOf(color));
    }

    @Override
    public void setNewCoordinates() {
        ch.setNewCoordinates();
    }

    @Override
    public void chooseTwoDice() {
        ch.chooseTwoDice();
    }

    @Override
    public void chooseTwoNewCoordinates() {
        ch.chooseTwoNewCoordinates();
    }

    @Override
    public boolean ping() {
        return true;
    }

    @Override
    public void sendDieFromWP(Die d, int row, int column) throws RemoteException {
        server.setDieFromWP(row, column);
    }

    @Override
    public void sendDieFromDP(Die d) throws RemoteException {
        server.setDieFromDP(d);
    }

    @Override
    public void sendDieFromRT(Die d, int round) throws RemoteException {
        server.setDieFromRT(d, round);
    }

    @Override
    public void sendDecreaseChoice(boolean choice) throws RemoteException {
        server.setDecrease(choice);
    }

    @Override
    public void sendPlacementChoice(boolean choice) throws RemoteException {
        server.setPlacementChoice(choice);
    }

    @Override
    public void sendNumberDiceChoice(boolean choice) throws RemoteException {
        server.setNumberDiceChoice(choice);
    }

    @Override
    public void sendValue(int value) throws RemoteException {
        server.setValue(value);
    }


    @Override
    public void sendNewCoordinates(int row, int column) throws RemoteException {
        server.setNewCoordinates(row, column);
    }

    @Override
    public void sendTwoDice(int row1, int col1, int row2, int col2) throws RemoteException {
        server.setTwoDice(row1, col1, row2, col2);
    }

    @Override
    public void sendTwoNewCoordinates(int row1, int col1, int row2, int col2) throws RemoteException {
        server.setTwoNewCoordinates(row1, col1,row2, col2);
    }

    @Override
    public void nextMove() {
        ch.nextMove();
    }

    @Override
    public void pushTokens(String name, String tool, int cost) {
        ch.updateTokens(name, tool, cost);
    }

    @Override
    public void notifyMoveDie(Player player, Die d, int row, int column, int newRow, int newColumn) {
        ch.handleMoveDie(player.getName(), d, row, column, newRow, newColumn);
    }

    @Override
    public void notifyAddDie(Player player, Die d, int row, int column) {
        ch.handleAddDie(player.getName(), d, row, column);
    }


    @Override
    public void changeTurn(Player first) {
        ch.handleChangeTurn(first.getName());
    }

    @Override
    public void updateRoundTrack(Die d, int diePosition, int round) {
        ch.handleUpdateRoundTrack(d, diePosition, round);
    }

    @Override
    public void updateGrid(int row, int col, Die d, String name) {
        ch.handleUpdateGrid(name, row, col, d);
    }

    @Override
    public void notifyEndTool(boolean response, String name) {
        ch.handleToolEnd(response, name);
    }

    @Override
    public void reconnection() {
        ch.reconnection();
    }

    @Override
    public void endGame(List<Player> players) {
        ch.endGame(players);
    }

    @Override
    public void finishUpdate(String name) {
        ch.finishUpdate(name);
    }

    @Override
    public void notifyMoveNotAvailable() throws RemoteException {
        ch.notifyMoveNotAvailable();
    }


    @Override
    public void requestTool(ToolCard tool) throws IOException {
        server.requestTool(getName(),tool.getName());
    }

    private class Ping implements Runnable{
        ServerInterface server;

        public Ping(ServerInterface server){
            this.server=server;
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
                            isAlive[0] = server.clientPing();

                        }else{
                            isAlive[0] = false;
                            //System.out.println("[DEBUG] Ping sent");
                            timer2.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (!isAlive[0]) {
//                                        System.out.println("[DEBUG] Client disconnected");
                                        this.cancel();
                                        timer1.cancel();
                                        isTimerRunning[0] = false;
                                        if(!ch.getModel().getMyself().getStatus().equals(PlayerStatus.RECONNECTED))
                                            ch.handleDisconnection();

                                    }
                                }
                            }, 8*1000);
                            isAlive[0] = server.clientPing();

                        }
                    }  catch (IOException e) {
                        e.getMessage();
                        isTimerRunning[0] = false;
                        timer1.cancel();
                        timer2.cancel();
                        if(!ch.getModel().getMyself().getStatus().equals(PlayerStatus.RECONNECTED))
                            ch.handleDisconnection();
                    }
                }
            }, 4 * 1000, 5 * 1000 );
        }
    }

}
