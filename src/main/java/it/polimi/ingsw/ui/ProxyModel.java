package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ProxyModel  {
    private ObservableList<Die> draftPool = FXCollections.observableArrayList();
    private Player myself;
    private Player currentPlayer;
    private ObservableList<Player> players = FXCollections.observableArrayList();
    private IntegerProperty currentRound;
    private IntegerProperty currentTurn;
    private List<ObjCard> publicObjectiveCards = new ArrayList<>();

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    private List<ToolCard> toolCards = new ArrayList<>();
    private RoundTrack roundTrack;
    private int timeout;

    public ProxyModel() {
        roundTrack = new RoundTrack();
        this.currentRound = new SimpleIntegerProperty(-1);
        this.currentTurn = new SimpleIntegerProperty(-1);
    }
    public boolean isMyTurn() {
        return myself.equals(currentPlayer);
    }

    public ObservableList<Die> getDraftPool() {
        return draftPool;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<ObjCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }


    public void addDiceToRoundTrack(List<Die> d){
        roundTrack.addRound(d);
    }

    public int getCurrentRound() {
        return currentRound.get();
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound.set(currentRound);
    }

    public IntegerProperty currentRoundProperty() {
        return currentRound;
    }

    public int getCurrentTurn() {
        return currentTurn.get();
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn.set(currentTurn);
    }

    public IntegerProperty currentTurnProperty() {
        return currentTurn;
    }

    public void setPlayer(Player p){
        this.myself= p;
    }

    public void setTimeout(int timeout){
        this.timeout= timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public Player getMyself(){
        return myself;
    }

    public void addPlayers(Player p) {
        players.add(p);
    }

    public void addPlayers(List<Player> l) {
        players.addAll(l);
    }

    public void removePlayer (Player p){
        for(Player player : players){
            if(player.getName().equals(p.getName())){
                players.remove(player);
                break;
            }
        }
    }

    public void resetPlayers(List<Player> l) {
        players.clear();
        for (Player p : l){
            if(!p.getName().equals(getMyself().getName())){
                players.add(p);
            }
        }
    }

    public ObservableList<Player> getPlayers(){
        return this.players;
    }

    public void addDice(List<Die> l) {
        draftPool.addAll(l);
    }

    public void addDie(Die d) {
        draftPool.add(d);
    }

    public void removeDie(Die d) {
        draftPool.remove(d);
    }

    public void addPubObjCards(List<PublicObjectiveCard> publicObjectiveCards){
        this.publicObjectiveCards.addAll(publicObjectiveCards);
    }

    public void addToolCard(List<ToolCard> tools){
        this.toolCards.addAll(tools);
    }
    public void setDraftPool(List<Die> draftPool){
        this.draftPool.clear();
        this.draftPool.addAll(draftPool);
    }
    public void setCurrentPlayer(Player player){
        this.currentPlayer= player;
    }

    public Player getByName(String name) {
        for (Player p : players) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return new Player("PlayerNotFound"); //TODO change this into something better
    }
}
