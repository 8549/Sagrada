package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.*;

import java.util.List;

public abstract class ClientObject  {
    protected ServerInterface server;
    protected Player player;

    public abstract void pushPlayers(List<Player> players);
    public abstract void pushLoggedPlayer(Player player);
    public abstract void notifyPlayerDisconnection(Player p);
    public abstract void notifyGameStarted(List<Player> players);
    public abstract void requestPatternCardChoice(List<PatternCard> patternCards);
    public abstract void pushPatternCardResponse(String name);
    public abstract void pushOpponentsInit(List<Player> thinPlayers);
    public abstract void pushPublicObj(ObjCard[] publicObj);
    public abstract void setPrivObj(ObjCard privObj, List<Player> players);
    public abstract void pushDraft(List<Die> draft);
    public abstract void notifyTurn(Player p);
    public abstract void notifyMoveResponse(boolean response, String type);

    public Player getPlayer(){
        return this.player;

    }

}
