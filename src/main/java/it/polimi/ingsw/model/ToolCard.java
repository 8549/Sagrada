package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.effect.Effect;

import java.util.List;

//TODO
public class ToolCard {
    private List<Effect> effects;
    private GameManager gameManager;
    private int tokens;
    private boolean used;
    private String name;
    private int id;
    private String when;
    private List<String> features;
    private int newRow;
    private int newColumn;
    private int oldRow;
    private int oldColumn;
    private int value;
    private Die die;
    int turnForRoundTrack;
    int numberOfDieForRoundTrack;
    private Player player;
    private boolean decrease;
    private boolean placeDie;


    public boolean isUsed() {
        return used;
    }

    public int getCost() {
        if (used) {
            return 2;
        } else {
            return 1;
        }
    }

    public void addTokens() {
        tokens = tokens + getCost();
    }

    public void useTools() {
        for (Effect effect : effects){

        }
        used = true;
        addTokens();
    }

    public int getTokens() {
        return tokens;
    }


    //METHODS FOR EFFECTS
    public boolean addDieToDiceBag(Die die) {
        return gameManager.getBoard().getDiceBag().addDie(die);
    }


    public boolean processMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place){
        setNewCoordinates();
        if(adjacency){
            setOldCoordinates();
        }
        MoveValidator moveValidator = new MoveValidator(gameManager.getRound().getTurn(), gameManager.getRound().getDraftPool(), number, color, adjacency);
        if(moveValidator.validateMove(die, newRow, newColumn, player)){
            if(!adjacency || place){
                player.getPlayerWindow().addDie(die, newRow, newColumn);
                gameManager.getRound().getTurn().setDiePlaced();
            }else{
                player.getPlayerWindow().moveDie(oldRow, oldColumn, newRow, newColumn);
            }
            return true;
        }
        return false;
    }

    public void getDieFromDicePool(){
        die=gameManager.getBoard().getDiceBag().draftDie();
    }


    public void chooseDieFromWindowPattern(){
        setOldCoordinates();
        die=player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
    }

    public int getValue(){return value;}


    public void chooseDieFromDraftPool(){} //TODO

    public void chooseDieFromRoundTrack(){} //TODO

    public void chooseIfDecrease(){} //TODO

    public void chooseIfPlaceDie(){} //TODO

    public void setValue(){} //TODO

    public void setOldCoordinates(){} //TODO

    public void setNewCoordinates(){ } //TODO

}
