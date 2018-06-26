package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.*;

import java.util.List;

import static it.polimi.ingsw.GameManager.PUBLIC_OBJ_CARDS_NUMBER;

public class SocketClientObject implements ClientObject {
    private ServerInterface server;
    private Player player;
    List<ClientObject> clients;
    SocketServer.SocketHandler socketHandler;

    public SocketClientObject(Player player, ServerInterface server, SocketServer.SocketHandler socketHandler){
        this.player = player;
        this.server = server;
        this.socketHandler = socketHandler;
    }


    @Override
    public void pushPlayers(List<Player> players) {
        String data ="";
        if(players!=null){
            for (Player p :players){
                data = data + p.getName() + "/";
            }
        }else{
            data ="You are the first player!";
        }
        socketHandler.send("update","loggedPlayers",data);
    }

    @Override
    public void pushLoggedPlayer(Player player) {
        socketHandler.send("update", "loggedPlayer", player.getName());
    }

    @Override
    public void notifyPlayerDisconnection(Player p) {
        System.out.println("Sending disconnection ");
        socketHandler.send("update", "disconnectedPlayer", p.getName());
    }

    @Override
    public void notifyGameStarted(List<Player> players) {
        String data="";
        for (Player p :players){
            if (!p.getName().equals(this.player.getName())) {
                data = data + p.getName() + "/";
            }
        }

        socketHandler.send("update", "gameStarted", data);
    }

    @Override
    public void requestPatternCardChoice(List<PatternCard> patternCards) {
        String data="";
        for (PatternCard p : patternCards){
            data = data + p.getName() +"/";
        }
        socketHandler.send("request", "initPattern", data);
    }

    @Override
    public void pushPatternCardResponse(String name) {
        socketHandler.send("update", "patterncardValidation", name);
    }

    @Override
    public void pushOpponentsInit(List<Player> thinPlayers) {
        String data = "";
        for (Player p : thinPlayers){
            if (!p.getName().equals(this.player.getName())) {
                data = data + p.getName() + "/" + p.getPlayerWindow().getWindowPattern().getName() + "/";
            }
        }

        socketHandler.send("update", "opponentsInfo", data);
    }

    @Override
    public void pushPublicObj(PublicObjectiveCard[] publicObj) {
        String data="";
        for (int i=0; i < PUBLIC_OBJ_CARDS_NUMBER; i++ ){
            data = data + publicObj[i].getName() + "/";
        }
        socketHandler.send("update","publicObj", data);
    }

    @Override
    public void setPrivObj(String name, List<Player> players) {
        String data ="";
        data = this.getPlayer().getName() + "/" + name + "/";
        for (Player p :players){
            if (!p.getName().equals(this.player.getName())) {
                data = data + p.getName() + "/blank/";
            }
        }
        socketHandler.send("update", "privObj", data);
    }

    @Override
    public void pushDraft(List<Die> draft) {
        String data = "";
        for (Die d: draft){
            data = data + d.getColor() + "/" + d.getNumber() + "/";
        }
        socketHandler.send("update","draftPool", data);
    }

    @Override
    public void notifyTurn(Player p, int round, int turn) {
        socketHandler.send("update", "turnStarted", p.getName() + "/" + round + "/" + turn);
    }

    @Override
    public void notifyMoveResponse(boolean response, String type) {
        if(response) {
            socketHandler.send(type, "moveResponse","Move accepted");
        }else{
            socketHandler.send(type, "moveResponse", "Wrong move");

        }    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void answerLogin(boolean response) {

    }
}
