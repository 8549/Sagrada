package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.util.List;

public interface ClientObject  {
    public  void pushPlayers(List<Player> players) throws IOException;
    public  void pushLoggedPlayer(Player player)throws IOException;
    public  void notifyPlayerDisconnection(Player p)throws IOException;
    public  void notifyGameStarted(List<Player> players)throws IOException;
    public  void requestPatternCardChoice(List<PatternCard> patternCards)throws IOException;
    public  void pushPatternCardResponse(String name)throws IOException;
    public  void pushOpponentsInit(List<Player> thinPlayers)throws IOException;
    public  void pushPublicObj(PublicObjectiveCard[] publicObj)throws IOException;
    public  void pushToolCards(List<ToolCard> tools)throws IOException;
    public  void setPrivObj(String name, List<Player> players)throws IOException;
    public  void pushDraft(List<Die> draft)throws IOException;
    public  void notifyTurn(Player p, int round, int turn)throws IOException;
    public  void notifyMoveResponse(boolean response, String type)throws IOException;
    public void notifyEndTimeOut() throws IOException;
    public void notifyEndTurn(Player p) throws IOException;

    public Player getPlayer()throws IOException;

    public  void answerLogin(boolean response)throws IOException;
}
