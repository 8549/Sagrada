package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.ObjCard;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RMIClientObject implements RMIClientObjectInterface, RMIServerInterface {
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
        new Thread(){
            public void run(){
                try {
                    client.addPlayersToProxy(p);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        return;
    }

    @Override
    public void pushLoggedPlayer(Player player) {
        new Thread(){
            public void run(){
                try {
                    client.addPlayerToProxy(new Player(player.getName()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        return;
    }

    @Override
    public void notifyPlayerDisconnection(Player p) {

    }

    @Override
    public void notifyGameStarted(List<Player> players) {
        new Thread(){
            public void run(){
                try {
                    client.initGame(players);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        return;
    }

    @Override
    public void requestPatternCardChoice(List<PatternCard> patternCards) {
        List<PatternCard> patterns = new ArrayList<>();
        patterns.addAll(patternCards);
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

    }

    @Override
    public void pushOpponentsInit(List<Player> thinPlayers) {

    }

    @Override
    public void pushPublicObj(ObjCard[] publicObj) {

    }

    @Override
    public void setPrivObj(ObjCard privObj, List<Player> players) {

    }

    @Override
    public void pushDraft(List<Die> draft) {

    }

    @Override
    public void notifyTurn(Player p) {

    }

    @Override
    public void notifyMoveResponse(boolean response, String type) {

    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void answerLogin(boolean response){
        new Thread(){
            public void run(){
                try {
                    client.loginResponse(response);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void start(String[] args) throws RemoteException {

    }

    @Override
    public void login(Player p, RMIClientInterface c) throws RemoteException {

    }

    @Override
    public void patternCardValidation(String patternName, RMIClientInterface c) throws RemoteException {

    }

    @Override
    public RMIServerInterface getNewStub() throws RemoteException {
        return null;
    }
}
