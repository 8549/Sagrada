package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.ServerInterface;

import java.io.IOException;
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
    public void notifyGameStarted(List<Player> players, int timeout) {
        String data="";
        for (Player p :players){
            if (!p.getName().equals(this.player.getName())) {
                data = data + p.getName() + "/";
            }
        }

        socketHandler.send("update", "gameStarted", data  + timeout);
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
                data = data + p.getName() + "/" + p.getPlayerWindow().getWindowPattern().getName() + "/" + p.getTokens();
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
    public void pushToolCards(List<String> tools) throws IOException {
        String data="";
        for(String tool : tools){
            data = data + "/" + tool;
        }
        socketHandler.send("update", "tools", data);
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
    public void notifyMoveResponse(boolean response, String name , Die d, int row, int column) {
        if(response) {
            socketHandler.send("response", "moveResponse",name + "/" + d.getNumber() + "/" + d.getColor() + "/" + row + "/" + column);
        }else{
            socketHandler.send("response", "moveResponse", name + "/false");

        }
    }

    @Override
    public void notifyEndTimeOut() throws IOException {
        socketHandler.send("update", "moveTimer","TimeIsOut" );
    }

    @Override
    public void notifyEndTurn(Player p) throws IOException {
        socketHandler.send("update", "endTurn",p.getName() );
    }

    @Override
    public void notifyEndRound(List<Die> dice) throws IOException {
        String data = "";
        for (Die d : dice){
            data = data + d.getColor() + "/" + d.getNumber() + "/";
        }
        socketHandler.send("update", "endRound", data);
    }

    @Override
    public void nextMove() throws IOException {
        socketHandler.send("update", "nextMove", "");

    }

    @Override
    public void notifyToolCardResponse(boolean response) throws IOException {
        if(response){
            socketHandler.send("response", "tool","true");
        }else{
            socketHandler.send("response", "tool", "false");
        }
    }

    @Override
    public void pushTokens(String name, String tool, int cost) throws IOException {
        socketHandler.send("update", "toolTokens", name + "/" + tool + "/" + cost);
    }

    @Override
    public void notifyMoveNotAvailable() throws IOException {
        socketHandler.send("update", "moveNotAvailable","");
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void answerLogin(boolean response) {
        socketHandler.send("response", "login", "true");
    }

    @Override
    public void chooseDieFromWindowPattern() throws IOException {
        socketHandler.send("request", "chooseDieFromWindowPattern", "");

    }

    @Override
    public void chooseDieFromDraftPool() throws IOException {
        socketHandler.send("request", "chooseDieFromDraftPool", "");

    }

    @Override
    public void chooseDieFromRoundTrack() throws IOException {
        socketHandler.send("request", "chooseDieFromRoundTrack", "");

    }

    @Override
    public void chooseIfDecrease() throws IOException {
        socketHandler.send("request", "chooseIfDecrease", "");

    }

    @Override
    public void chooseIfPlaceDie() throws IOException {
        socketHandler.send("request", "chooseIfPlaceDie", "");

    }

    @Override
    public void chooseToMoveOneDie() throws IOException {
        socketHandler.send("request", "chooseToMoveOneDie", "");

    }

    @Override
    public void setValue(String color) throws IOException {
        socketHandler.send("request", "setValue", color);

    }


    @Override
    public void setNewCoordinates() throws IOException {
        socketHandler.send("request", "setNewCoordinates", "");

    }

    @Override
    public void chooseTwoDice() throws IOException {
        socketHandler.send("request", "choooseTwoDice", "");
    }

    @Override
    public void chooseTwoNewCoordinates() throws IOException {
        socketHandler.send("request", "chooseTwoNewCoordinates", "");
    }

    @Override
    public void notifyToolUsed(boolean result,String name) throws IOException {
        if(result){
            socketHandler.send("update", "toolEnd", "true"+ "/" + name);
        }else{
            socketHandler.send("update", "toolEnd", "false" + "/" + name);

        }

    }

    @Override
    public void moveDie(Player player, Die d, int row, int column, int newRow, int newColumn) throws IOException {
        socketHandler.send("update", "moveDieTool", player.getName() + "/" + d.getColor() + "/" + d.getNumber() + "/" + row + "/" + column + "/" + newRow + "/" + newColumn);
     }

    @Override
    public void addDie(Player player, Die d, int row, int column) throws IOException {
        socketHandler.send("update", "addDieTool", player.getName() + "/" + d.getColor() + "/" + d.getNumber() + "/" + row + "/" + column );

    }

    @Override
    public void changeTurn(Player p) throws IOException {
        socketHandler.send("update", "changeTurn", p.getName());
    }


    @Override
    public void updateRoundTrack(Die d, int diePosition, int round) throws IOException{

        socketHandler.send("update", "updateRoundTrack", round + "/" +   d.getColor() + "/" + d.getNumber() + "/" + diePosition);
    }

    @Override
    public void updateGrid(int row, int col, Die d, String name) throws IOException {
        socketHandler.send("update", "updateGrid", name + "/" + row + "/" + col + "/" + d.getColor() + "/" + d.getNumber());
    }

    @Override
    public void reconnection() throws IOException {
        socketHandler.send("update", "reconnection","");
    }
}
