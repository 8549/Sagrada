package it.polimi.ingsw;

public class Player {
    private String name;
    private final PlayerWindow playerWindow;
    private ObjCard privateObjectiveCard;
    private boolean privateObjectiveCardSet;
    private int tokens;

    public Player(String name) {
        this.name = name;
        this.playerWindow = new PlayerWindow();
        this.privateObjectiveCardSet = false;
    }


    /**
     * Set privateObjectiveCard if it wasn't already set
     *
     * @param privateObjectiveCard
     * @return True if is set, otherwise false
     */
    boolean setPrivateObjectiveCard(ObjCard privateObjectiveCard) {
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

    @Override
    public String toString() {
        return name + " ";
    }
}
