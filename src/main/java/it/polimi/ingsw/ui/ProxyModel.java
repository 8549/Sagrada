package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PublicObjectiveCard;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProxyModel  {
    ObservableList<Die> draftPool = FXCollections.observableArrayList();
    Player myself;
    Player currentPlayer;
    ObservableList<Player> players = FXCollections.observableArrayList();
    IntegerProperty currentRound;
    IntegerProperty currentTurn;
    List<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();

    public ProxyModel() {
        this.currentRound = new SimpleIntegerProperty();
        this.currentTurn = new SimpleIntegerProperty();
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
        players.addAll(l);
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

    public void setDraftPool(List<Die> draftPool){
        this.draftPool.clear();
        this.draftPool.addAll(draftPool);
    }
    public void setCurrentPlayer(Player player){
        this.currentPlayer= player;
    }
}
