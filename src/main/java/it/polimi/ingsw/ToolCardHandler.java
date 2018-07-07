package it.polimi.ingsw;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SagradaColor;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;

import java.io.IOException;
import java.util.List;

public class ToolCardHandler {
    private GameManager gm;
    private MainServer server;
    protected ToolCard toolcard;
    private Player player;
    private boolean isActive=false;
    private SagradaColor colorOfPickedDie;

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

    public void setColorOfPickedDie(SagradaColor color){
        this.colorOfPickedDie = color;
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
        toolcard.completeChooseDieFromWindowPattern(row, column);
        toolcard.completeSetOldCoordinates(row, column);

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
        toolcard.completeChooseDieFromDraftPool(d);
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
       int r= toolcard.getBoard().getRoundTrack().getDiePosition(round, d);
       toolcard.completeChooseDieRoundTrck(round, r);
        toolcard.checkHasNextEffect();
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
        toolcard.completeChoiceIfDecrease(choice);
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
        toolcard.completeChoiceIfPlaceDie(choice);
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
        toolcard.completeChoiceIfMoveOneDie(choice);
    }


    public void setValue() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.setValue(colorOfPickedDie.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void chosenValue(int value){
        toolcard.completeChooseValue(value);
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
        toolcard.completeProcessMove(row, column);
    }

    public void notifyPlayerDieAlreadyPlaced(){
        server.notifyMoveNotAvailable();
    }

    public void chooseTwoDieFromWindowPatter() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseTwoDice();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void setTwoDiceFromWindowPattern(int row, int column, int secondRow, int secondColumn){
            toolcard.completeChooseDieFromWindowPattern(row, column);
            toolcard.completeChooseTwoDieFromWindowPattern(secondRow, secondColumn);
            toolcard.completeSetOldCoordinates(row, column);
        }

    public void setTwoNewCoordinates() {
        for(ClientObject c : server.getInGameClients()){
            try {
                if(c.getPlayer().getName().equals(player.getName())){
                    c.chooseTwoNewCoordinates();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTwoNewCoordinatesChoice(int row, int column, int secondRow, int secondColumn){
            toolcard.completeProcessTwoMoves(row, column, secondRow, secondColumn);
    }
    public void pushNewTokens(int cost, Player player, String tool) {
        for(ClientObject c : server.getInGameClients()){
            try {
                c.pushTokens(player.getName(), tool, cost);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void updateDraftPool(List<Die> draft){
        for(ClientObject c : server.getInGameClients()){
            try {
                c.pushDraft(draft);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void notifyAddDie(Player player, Die d, int row, int column){
        for(ClientObject c : server.getInGameClients()){
            try {
                c.addDie(player, d, row, column);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyMoveDie(Player player, Die d, int row, int column, int newRow, int newColumn){
        for(ClientObject c : server.getInGameClients()){
            try {
                c.moveDie(player, d, row, column, newRow, newColumn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void notifyChangeTurn(Player first){
        for(ClientObject c : server.getInGameClients()){
            try {
                if(!first.getName().equals(c.getPlayer().getName())){
                    c.changeTurn(first);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            server.getClientByName(first.getName()).changeTurn(first);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void updateRoundTrack(Die d, int diePosition, int round){
        for(ClientObject c : server.getInGameClients()){
            try {
                c.updateRoundTrack(d, diePosition, round);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyPLayerOnlyOneDieWasMoved() {
        //notify player he was able ny to move one die, second move did not end well, but still we consider used the tool card
    }
}
