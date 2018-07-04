package it.polimi.ingsw.network.client;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.RMI.RMIServerInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class RMIClient implements RMIClientInterface, Serializable {
    Player player;
    RMIServerInterface server;
    ClientHandler ch;

    public RMIClient(ClientHandler ch) throws RemoteException{
        this.ch = ch;
    }

    public RMIClient() {

    }

    @Override
    public String getName() throws RemoteException {
        return player.getName();
    }

    @Override
    public void login() throws RemoteException {
        server.login(player,(RMIClientInterface) UnicastRemoteObject.exportObject(this,0));
    }


    @Override
    public void connect(String serverAddress, int portNumber, String userName) throws RemoteException{
        player = new Player(userName);
        Registry registry = LocateRegistry.getRegistry(serverAddress);

        try {
            server = (RMIServerInterface) registry.lookup("RMIServerInterface");
            System.out.println("[DEBUG] Client connected ");

        } catch (NotBoundException e) {
            e.printStackTrace();
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
    public void loginResponse(boolean response)throws RemoteException{
        if(response){
             System.out.println("[DEBUG] Login succesful");
            ch.setPlayerToProxyModel(player.getName());
        }else{
            System.out.println("[DEBUG] Login failed");
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
    public void setPrivateObj(String name) throws RemoteException {
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
    public void setPublicObj(List<PublicObjectiveCard> publicObjCards) throws RemoteException {
        ch.setPublicObjCard(publicObjCards);
    }

    @Override
    public void setDraft(List<Die> draft) throws RemoteException {
        ch.setDraftPool(draft);
    }

    @Override
    public void beginTurn(String name, int round, int turn) throws RemoteException {
        ch.notifyTurnStarted(name, round, turn);
    }

    @Override
    public void patternCardResponse(String name) throws RemoteException {
        ch.initPatternCard(name);
    }

    @Override
    public void initTools(List<String> names) throws RemoteException {
        List<ToolCard> tools = new ArrayList<>();
        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
        }.getType());
        for(String name : names){
            tools.add((ToolCard) toolDeck.getByName(name));
        }
        ch.setTools(tools);
    }

    @Override
    public void setEndPoint(RMIServerInterface server) throws RemoteException {
        this.server = server;
    }

    @Override
    public void updateOpponentsInfo(List<Player> players) throws RemoteException {
        for(Player p1 : ch.getModel().getPlayers()){
            for(Player p2 : players){
                if (p1.getName().equals(p2.getName())){
                    ch.initPlayer(p2.getName(), p2.getPlayerWindow().getWindowPattern().getName());
                }
            }
        }
    }

    @Override
    public void moveResponse(String name , boolean response, Die d, int row, int column) throws RemoteException {
        ch.handleMoveResponse(name, response, d, row, column);
    }

    @Override
    public void moveTimeOut() throws RemoteException {
        ch.moveTimeIsOut();
    }

    @Override
    public void endCurrentTurn(String name) throws RemoteException {
        ch.endTurn(name);
    }

    @Override
    public void endRound(List<Die> dice) throws RemoteException {
        ch.endRound(dice);
    }

    @Override
    public void chooseDieFromWindowPattern() throws RemoteException {
        ch.chooseDieFromWindowPattern();
    }

    @Override
    public void chooseDieFromDraftPool() throws RemoteException {
        ch.chooseDieFromDraftPool();
    }

    @Override
    public void chooseDieFromRoundTrack() throws RemoteException {
        ch.chooseDieFromRoundTrack();
    }

    @Override
    public void chooseIfDecrease() throws RemoteException {
        ch.chooseIfDecrease();
    }

    @Override
    public void chooseIfPlaceDie() throws RemoteException {
        ch.chooseIfPlaceDie();
    }

    @Override
    public void chooseToMoveOneDie() throws RemoteException {
        ch.chooseToMoveOneDie();
    }

    @Override
    public void setValue() throws RemoteException {
        ch.setValue();
    }


    @Override
    public void setNewCoordinates() throws RemoteException {
        ch.setNewCoordinates();
    }

    @Override
    public void ping() throws RemoteException {

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
    public void nextMove() throws RemoteException {
        ch.nextMove();
    }

    @Override
    public void notifyToolCardResponse(boolean response) throws RemoteException {
        ch.toolAvailable(response);
    }

    @Override
    public void requestTool(ToolCard tool) throws IOException {
        server.requestTool(getName(),tool.getName());
    }

}
