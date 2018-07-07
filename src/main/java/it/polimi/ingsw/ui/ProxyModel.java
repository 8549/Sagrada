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

    /**
     * Check if it is my turn or not
     *
     * @return true if this client (player) is currently playing, false otherwise
     */
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

    /**
     * Add the remaining dice of the draftpool of a round to the Round Track
     *
     * @param d
     */
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

    /**
     * Adds one {@link Player} to the players list
     *
     * @param p the player to add
     */
    public void addPlayers(Player p) {
        players.add(p);
    }

    /**
     * Adds one or more {@link Player}s to the players list
     *
     * @param l a list containing the player(s) to add
     */
    public void addPlayers(List<Player> l) {
        players.addAll(l);
    }

    /**
     * Removes one {@link Player}s from the players list
     *
     * @param p the player to remove
     */
    public void removePlayer (Player p){
        for(Player player : players){
            if(player.getName().equals(p.getName())){
                players.remove(player);
                break;
            }
        }
    }

    /**
     * Update the {@link Player}s list with the "official" list from the server. This method is called only once at th beginning
     * of the game
     *
     * @param l the players list
     */
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

    /**
     * Add a {@link Die} to the current draft pool
     *
     * @param d the die to add
     */
    public void addDie(Die d) {
        draftPool.add(d);
    }

    /**
     * Sets the {@link PublicObjectiveCard} for this game
     *
     * @param publicObjectiveCards a list containing the public objective cards for this game
     */
    public void addPubObjCards(List<PublicObjectiveCard> publicObjectiveCards){
        this.publicObjectiveCards.addAll(publicObjectiveCards);
    }

    /**
     * Setr the {@link ToolCard} for this game
     *
     * @param tools a list containing the tool cards for this game
     */
    public void addToolCard(List<ToolCard> tools){
        this.toolCards.addAll(tools);
    }

    /**
     * Clears the current draft pool and sets a new one
     *
     * @param draftPool a list of dice
     */
    public void setDraftPool(List<Die> draftPool){
        this.draftPool.clear();
        this.draftPool.addAll(draftPool);
    }

    /**
     * Updates the current {@link Player}. This method is called every turn
     *
     * @param player the new current player
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer= player;
    }

    /**
     * Retrieves a {@link Player} by their name
     * @param name the name of the player to find
     * @return the {@link Player} with the same name supplied if it exits, a mock player instance with the name set to
     * "PlayerNotFound"
     */
    public Player getByName(String name) {
        for (Player p : players) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return new Player("PlayerNotFound");
    }
}
