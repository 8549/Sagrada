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
    private Die die;
    private Player player;


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


    public boolean processMoveWithoutConstraints(Die die, int row, int column, Player player, boolean number, boolean color, boolean adjacency){
        MoveValidator moveValidator = new MoveValidator(gameManager.getRound().getTurn(), gameManager.getRound().getDraftPool(), number, color, adjacency);
        return moveValidator.validateMove(die, row, column, player);
    }

    public void skipTurn(){
        gameManager.endCurrentTurn();
    }

}
