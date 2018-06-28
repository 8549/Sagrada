package it.polimi.ingsw;

import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.network.server.MainServer;
import sun.tools.tree.ThisExpression;

public class ToolCardHandler {
    GameManager gm;
    MainServer server;
    ToolCard toolcard;

    public ToolCardHandler(GameManager gm, MainServer server, ToolCard toolCard){
        this.gm= gm;
        this.server= server;
        this.toolcard = toolCard;
    }
    public void chooseDieFromWindowPattern() {
    }

    public void chooseDieFromDraftPool() {
    }

    public void chooseDieFromRoundTrack() {
    }

    public void chooseIfDecrease() {
    }

    public void chooseIfPlaceDie() {
    }

    public void chooseToMoveOneDie() {
        //if he chooses to move just one die everythingIsOk is set to false so the tool card won't keep performing effects
    }

    public void setValue() {
    }

    public void setOldCoordinates() {
    }

    public void setNewCoordinates() {
    }

}
