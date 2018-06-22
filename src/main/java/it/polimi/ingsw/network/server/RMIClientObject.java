package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.ObjCard;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RMIClientObject extends ClientObject {
    RMIClientInterface client;

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
    public void notifyGameStarted(List<Player> players) {
        try {
            client.initGame(players);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestPatternCardChoice(List<PatternCard> patternCards) {
        List<PatternCard> patterns = new ArrayList<>();
        patterns.addAll(patternCards);
        try {
            client.initPatternCardChoice(patterns);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
}
