package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.client.RMIClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RMIClientObject implements RMIClientObjectInterface {
    private RMIClientInterface client;
    private ServerInterface server;
    private Player player;

    public RMIClientObject(Player player, RMIClientInterface client){
        this.player= player;
        this.client= client;
    }
    @Override
    public void pushPlayers(List<Player> players) {
        List<Player> p = new ArrayList<>();
        p.addAll(players);
        try {
            client.addPlayersToProxy(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pushLoggedPlayer(Player player) {

        try {
            client.addPlayerToProxy(new Player(player.getName()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyPlayerDisconnection(Player p) {

    }

    @Override
    public void notifyGameStarted(List<Player> players, int timeout) {
        List<Player> playerstoSend = new ArrayList<>();
        for (Player p : players){
            if (!p.getName().equals(this.getPlayer().getName())){
            playerstoSend.add(p);
            }

        }

        try {
            client.initGame(playerstoSend, timeout);
        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void requestPatternCardChoice(List<PatternCard> patternCards) {
        List<PatternCard> patterns = new ArrayList<>(patternCards);
        new Thread(){
            public void run(){
                try {
                    client.initPatternCardChoice(patterns);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return;
    }

    @Override
    public void pushPatternCardResponse(String name) {
        try {
            client.patternCardResponse(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushOpponentsInit(List<Player> thinPlayers) {
        List<Player> playerstoSend = new ArrayList<>();
        for (Player p : thinPlayers){
            if (!p.getName().equals(this.getPlayer().getName())){
                playerstoSend.add(p);
            }

        }
        try {
            client.updateOpponentsInfo(playerstoSend);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushPublicObj(PublicObjectiveCard[] publicObj) {
        List<PublicObjectiveCard> p = new ArrayList<>();
        p.addAll(Arrays.asList(publicObj));
        try {
            client.setPublicObj(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPrivObj(String name, List<Player> players) {
        try {
            client.setPrivateObj(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushDraft(List<Die> draft) {
    List<Die> d = new ArrayList<>();
    d.addAll(draft);
        try {
            client.setDraft(draft);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyTurn(Player p, int round, int turn) {
        try {
            client.beginTurn(p.getName(), round, turn);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyMoveResponse(boolean response, String name, Die d, int row, int column) {
        try {
            client.moveResponse(name,response,d, row, column);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void answerLogin(boolean response){
        try {
            client.loginResponse(response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyEndTimeOut() throws RemoteException {
        client.moveTimeOut();
    }

    @Override
    public void notifyEndTurn(Player p) throws RemoteException {
        client.endCurrentTurn(p.getName());
    }

    @Override
    public void notifyEndRound(List<Die> dice) throws RemoteException {
        client.endRound(dice);
    }

    @Override
    public void nextMove() throws RemoteException{
        client.nextMove();
    }

    @Override
    public void notifyToolCardResponse(boolean response) throws RemoteException {
        client.notifyToolCardResponse(response);
    }

    @Override
    public void ping() throws RemoteException {
        client.ping();
    }

    @Override
    public void pushToolCards(List<String> tools) throws RemoteException {
        List<String> names = new ArrayList<>();
        for(String tool: tools){
            names.add(tool);
        }
        client.initTools(names);
    }

    @Override
    public void chooseDieFromWindowPattern() throws RemoteException {
        client.chooseDieFromWindowPattern();
    }

    @Override
    public void chooseDieFromDraftPool() throws RemoteException {
        client.chooseDieFromDraftPool();
    }

    @Override
    public void chooseDieFromRoundTrack() throws RemoteException {
        client.chooseDieFromRoundTrack();
    }

    @Override
    public void chooseIfDecrease() throws RemoteException {
        client.chooseIfDecrease();
    }

    @Override
    public void chooseIfPlaceDie() throws RemoteException {
        client.chooseIfPlaceDie();
    }

    @Override
    public void chooseToMoveOneDie() throws RemoteException {
        client.chooseToMoveOneDie();
    }

    @Override
    public void setValue() throws RemoteException {
        client.setValue();
    }
    

    @Override
    public void setNewCoordinates() throws RemoteException {
        client.setNewCoordinates();
    }

    @Override
    public void notifyMoveNotAvailable() throws RemoteException {
    }


}
