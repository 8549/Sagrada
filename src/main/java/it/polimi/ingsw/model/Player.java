package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private final PlayerWindow playerWindow;
    private ObjCard privateObjectiveCard;
    private boolean privateObjectiveCardSet;
    private int tokens;
    List<PatternCard> choices;
    private boolean chosenPatternCard = false;
    private boolean isPlaying = false;

    public Player(String name) {
        this.name = name;
        this.playerWindow = new PlayerWindow();
        this.privateObjectiveCardSet = false;
        this.choices = new ArrayList<>();

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

    public List<PatternCard> getChoices(){
        return this.choices;
    }
    public void setChoices(List<PatternCard> choices){
        this.choices=choices;
    }

    public boolean hasChosenPatternCard(){
        return this.chosenPatternCard;
    }

    public void setHasChosenPatternCard(boolean chosen){
        this.chosenPatternCard = chosen;
    }




    @Override
    public String toString() {
        return name + " ";
    }
}

