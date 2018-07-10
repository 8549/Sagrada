package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PublicObjectiveCard;
import it.polimi.ingsw.network.SoxketHandlerInterface;
import it.polimi.ingsw.network.server.ClientObject;

import java.util.List;

import static it.polimi.ingsw.GameManager.PUBLIC_OBJ_CARDS_NUMBER;

public class SocketClientObject implements ClientObject {
    private Player player;
    int id;
    SoxketHandlerInterface socketHandler;

    public SocketClientObject(Player player, SoxketHandlerInterface socketHandler, int id){
        this.player = player;
        this.socketHandler = socketHandler;
        this.id= id;
    }

    public int getId(){
            return this.id;
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
            data = data + p.getName() + "/" + p.getPlayerWindow().getWindowPattern().getName() + "/" + p.getTokens() + "/";

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
    public void pushToolCards(List<String> tools) {
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
    public void notifyEndTimeOut(Player p) {
        socketHandler.send("update", "moveTimer",p.getName() );
    }

    @Override
    public void notifyEndTurn(Player p) {
        socketHandler.send("update", "endTurn",p.getName() );
    }

    @Override
    public void notifyEndRound(List<Die> dice) {
        String data = "";
        for (Die d : dice){
            data = data + d.getColor() + "/" + d.getNumber() + "/";
        }
        socketHandler.send("update", "endRound", data);
    }

    @Override
    public void nextMove() {
        socketHandler.send("update", "nextMove", "");

    }


    @Override
    public void pushTokens(String name, String tool, int cost) {
        socketHandler.send("update", "toolTokens", name + "/" + tool + "/" + cost);
    }

    @Override
    public void notifyMoveNotAvailable() {
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
    public void chooseDieFromWindowPattern() {
        socketHandler.send("request", "chooseDieFromWindowPattern", "");

    }

    @Override
    public void chooseDieFromDraftPool() {
        socketHandler.send("request", "chooseDieFromDraftPool", "");

    }

    @Override
    public void chooseDieFromRoundTrack() {
        socketHandler.send("request", "chooseDieFromRoundTrack", "");

    }

    @Override
    public void chooseIfDecrease() {
        socketHandler.send("request", "chooseIfDecrease", "");

    }

    @Override
    public void chooseIfPlaceDie(int number) {
        socketHandler.send("request", "chooseIfPlaceDie", "" + number);

    }

    @Override
    public void chooseToMoveOneDie() {
        socketHandler.send("request", "chooseToMoveOneDie", "");

    }

    @Override
    public void setValue(String color) {
        socketHandler.send("request", "setValue", color);

    }


    @Override
    public void setNewCoordinates() {
        socketHandler.send("request", "setNewCoordinates", "");

    }

    @Override
    public void chooseTwoDice() {
        socketHandler.send("request", "choooseTwoDice", "");
    }

    @Override
    public void chooseTwoNewCoordinates() {
        socketHandler.send("request", "chooseTwoNewCoordinates", "");
    }

    @Override
    public void notifyToolUsed(boolean result, String name) {
        if(result){
            socketHandler.send("update", "toolEnd", "true"+ "/" + name);
        }else{
            socketHandler.send("update", "toolEnd", "false" + "/" + name);

        }

    }

    @Override
    public void moveDie(Player player, Die d, int row, int column, int newRow, int newColumn) {
        socketHandler.send("update", "moveDieTool", player.getName() + "/" + d.getColor() + "/" + d.getNumber() + "/" + row + "/" + column + "/" + newRow + "/" + newColumn);
     }

    @Override
    public void addDie(Player player, Die d, int row, int column) {
        socketHandler.send("update", "addDieTool", player.getName() + "/" + d.getColor() + "/" + d.getNumber() + "/" + row + "/" + column );

    }

    @Override
    public void changeTurn(Player p) {
        socketHandler.send("update", "changeTurn", p.getName());
    }


    @Override
    public void updateRoundTrack(Die d, int diePosition, int round) {

        socketHandler.send("update", "updateRoundTrack", round + "/" +   d.getColor() + "/" + d.getNumber() + "/" + diePosition);
    }

    @Override
    public void updateGrid(int row, int col, Die d, String name) {
        socketHandler.send("update", "updateGrid", name + "/" + row + "/" + col + "/" + d.getColor() + "/" + d.getNumber());
    }

    @Override
    public void pushFinalScore(List<Player> players) {
        String data ="";
        for(Player p : players){
            String pointString = (p.getPoints() < 0) ? "n/" + (Math.abs(p.getPoints())) : "p/" + p.getPoints();
            data = data + p.getName() + "/" + pointString + "/";
        }

        socketHandler.send("update", "endGame", data);
    }

    @Override
    public void reconnection() {
        socketHandler.send("update", "reconnection","");
    }

    @Override
    public void notifyFinishUpdate(String name) {
        socketHandler.send("update", "finishUpdate", name);
    }

}
