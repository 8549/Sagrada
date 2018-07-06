package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private final PlayerWindow playerWindow;
    private ObjCard privateObjectiveCard;
    private boolean privateObjectiveCardSet;
    private int tokens;
    List<PatternCard> choices;
    private boolean chosenPatternCard = false;
    private boolean isPlaying = false;
    private int points;
    private PlayerStatus status;

    public Player(String name) {
        this.name = name;
        this.playerWindow = new PlayerWindow();
        this.privateObjectiveCardSet = false;
        this.choices = new ArrayList<>();
        status = PlayerStatus.ACTIVE;
        points=0;
    }


    /**
     * Set privateObjectiveCard if it wasn't already set
     *
     * @param privateObjectiveCard
     * @return True if is set, otherwise false
     */
    public boolean setPrivateObjectiveCard(ObjCard privateObjectiveCard) {
        if (!privateObjectiveCardSet) {
            this.privateObjectiveCard = privateObjectiveCard;
            privateObjectiveCardSet = true;
            return true;
        } else {
            return false;
        }

    }

    public void setStatus(PlayerStatus status){
        this.status=status;
    }

    public PlayerStatus getStatus(){ return status; }
    public PlayerWindow getPlayerWindow() {
        return playerWindow;
    }

    public String getName() {
        return name;
    }

    public ObjCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public void setInitialTokens() {
        this.tokens = playerWindow.getWindowPattern().getDifficulty();
    }

    public int getTokens(){ return tokens;}

    public void removeTokens(int numOfTokens) {
        tokens = tokens - numOfTokens;
    }

    public List<PatternCard> getChoices() {
        return this.choices;
    }
    public void setChoices(List<PatternCard> choices){
        this.choices.addAll(choices);
    }

    public boolean hasChosenPatternCard() {
        return this.chosenPatternCard;
    }

    public void setHasChosenPatternCard(boolean chosen) {
        this.chosenPatternCard = chosen;
    }

    public void addPoints(int points){
        this.points=+points;
    }

    public void subPoints(int points) {this.points=-points; }

    @Override
    public String toString() {
        return name;
    }

    public int getPoints() {
        return points;
    }

}

