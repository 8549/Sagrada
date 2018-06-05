package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class SocketClientObject extends ClientObject {

    List<ClientObject> clients;
    SocketServer.SocketHandler socketHandler;

    public SocketClientObject(Player player, ServerInterface server, SocketServer.SocketHandler socketHandler){
        this.player = player;
        this.server = server;
        this.socketHandler = socketHandler;
    }
    @Override
    public void login() {

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
            data = data + p.getName() + "/";
        }
        socketHandler.send("update", "gameStarted", data);
    }

    @Override
    public void requestPatternCardChoice(List<PatternCard> patternCards) {
        String data="";
        for (PatternCard p : patternCards){
            data = p.getName() + "/" ;
        }
        socketHandler.send("request", "initPattern", data);
    }
}
