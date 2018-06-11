package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PublicObjectiveCard;

import java.util.List;

public abstract class ClientObject  {
    ServerInterface server;
    Player player;

    public abstract void login();
    public abstract void pushPlayers(List<Player> players);
    public abstract void pushLoggedPlayer(Player player);
    public abstract void notifyPlayerDisconnection(Player p);
    public abstract void notifyGameStarted(List<Player> players);
    public abstract void requestPatternCardChoice(List<PatternCard> patternCards);
    public abstract void pushPatternCardResponse(String name);
    public abstract void pushOpponentsInit(List<Player> thinPlayers);
    public abstract void pushPublicObj(List<PublicObjectiveCard> publicObj);

    public Player getPlayer(){
        return this.player;

    }

}
