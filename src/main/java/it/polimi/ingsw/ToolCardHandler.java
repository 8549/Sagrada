package it.polimi.ingsw;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;

import java.io.IOException;

public class ToolCardHandler {
    private GameManager gm;
    private MainServer server;
    private ToolCard toolcard;
    private Player player;
    private boolean isActive=false;

    public ToolCardHandler(Player p,GameManager gm, MainServer server, ToolCard toolCard){
        this.gm= gm;
        this.server= server;
        this.toolcard = toolCard;
        this.player = p;
    }

    public void setActive(boolean active){
        this.isActive= active;
    }

    public boolean isActive(){
        return this.isActive;
    }

    public void chooseDieFromWindowPattern() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseDieFromWindowPattern();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDieFromWindowPattern(int row, int column){

    }

    public void chooseDieFromDraftPool() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseDieFromDraftPool();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDieFromDraftPool(Die d){

    }

    public void chooseDieFromRoundTrack() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseDieFromRoundTrack();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDieFromRoundTrack(Die d, int round){

    }

    public void chooseIfDecrease() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseIfDecrease();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDecreaseChoice(boolean choice){

    }


    public void chooseIfPlaceDie() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseIfPlaceDie();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIfPlace(boolean choice){

    }

    public void chooseToMoveOneDie() {
        //if he chooses to move just one die everythingIsOk is set to false so the tool card won't keep performing effects
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseToMoveOneDie();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMovementChoice(boolean choice){

    }


    public void setValue() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.setValue();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void choseValue(int value){

    }

    public void setOldCoordinates() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.setOldCoordinates();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOldCoordinatesChoice(int row, int column){

    }

    public void setNewCoordinates() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.setNewCoordinates();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNewCoordinatesChoice(int row, int column){

    }

    public void notifyPlayerDieAlreadyPlaced(){
        server.notifyMoveNotAvailable();
    }

}
