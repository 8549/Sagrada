package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.effect.Effect;
import it.polimi.ingsw.network.server.MainServer;

import java.util.Iterator;
import java.util.List;

//TODO
public class ToolCard {
    private List<Effect> effects;
    private GameManager gameManager;
    private ToolCardHandler toolCardHandler;
    private Player player;
    private int tokens;
    private boolean used;
    private String name;
    private int id;
    private String when;
    private List<String> features;
    //attributes for effects
    private Die die;
    int turnForRoundTrack;
    int numberOfDieForRoundTrack;
    private boolean decrease;
    private boolean placeDie;
    private boolean moveOneDie;
    private boolean number;
    private boolean color;
    private boolean adjacency;
    private boolean place;
    private Iterator effectIterator;
    private boolean everythingOk;

    public ToolCard(String name, int id, String when, List<String> features) {
        effectIterator = effects.iterator();
        everythingOk=true;
        this.name=name;
        this.id=id;
        this.when=when;
        this.features=features;
    }


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

    public void useTools(Player player, GameManager gameManager, MainServer server) {
        this.player=player;
        this.gameManager=gameManager;
        toolCardHandler = new ToolCardHandler(gameManager, server, this);
        performEffect();

    }

    public void performEffect() {
        Effect currentEffect;
        if (effectIterator.hasNext() && everythingOk) {
            currentEffect = (Effect) effectIterator.next();
            switch (currentEffect.getName()) {
                case "addDieToDicePool":
                    currentEffect.perform(die);
                    checkHasNextEffect();
                    break;
                case "changeTurnOrder":
                    currentEffect.perform(gameManager.getRound());
                    checkHasNextEffect();
                    break;
                case "checkIsDiePlaced":
                    currentEffect.perform(gameManager.getRound().getTurn());
                    checkHasNextEffect();
                    break;
                case "checkIsSecondTurn":
                    currentEffect.perform(gameManager.getRound());
                    checkHasNextEffect();
                    break;
                case "chooseDieFromDraftPool":
                    currentEffect.perform();
                    break;
                case "chooseDieFromRoundTrack":
                    currentEffect.perform();
                    break;
                case "chooseDieFromWindowPattern":
                    currentEffect.perform();
                    break;
                case "chooseDieValue":
                    currentEffect.perform(die);
                    break;
                case "chooseIfDecreaseOrIncreaseValue" :
                    currentEffect.perform();
                    break;
                case "chooseIfPlaceDieOrPlaceDieInDraftPool":
                    currentEffect.perform();
                    break;
                case "chooseToMoveOneOrTwoDice" :
                    currentEffect.perform();
                    break;
                case "decreaseValueDie":
                    currentEffect.perform(die, decrease);
                    checkHasNextEffect();
                    break;
                case "flipDie":
                    currentEffect.perform(die);
                    checkHasNextEffect();
                    break;
                case  "getDieFromDicePool":
                    currentEffect.perform();
                    checkHasNextEffect();
                    break;
                case "increaseValueDie":
                    currentEffect.perform(die, decrease);
                    checkHasNextEffect();
                    break;
                case "moveDie":
                    currentEffect.perform();
                    break;
                case "moveDieWithoutColorConstraint":
                    currentEffect.perform();
                    break;
                case "moveDieWithoutNumberConstraint":
                    currentEffect.perform();
                    break;
                case "moveDieWithSameColorAsDieFromRoundTrack":
                    currentEffect.perform();
                    break;
                case "placeDie":
                    currentEffect.perform();
                    break;
                case "placeDieInDraftPool":
                    currentEffect.perform(placeDie, gameManager.getBoard(), die);
                    checkHasNextEffect();
                    break;
                case "placeDieWithoutAdjacencyConstraint":
                    currentEffect.perform();
                    break;
                case "replaceDieOnRoundTrack":
                    currentEffect.perform(die, turnForRoundTrack, numberOfDieForRoundTrack, gameManager.getBoard());
                    checkHasNextEffect();
                    break;
                case "rollAllDice":
                    currentEffect.perform(gameManager.getRound());
                    checkHasNextEffect();
                    break;
                case "rollDie":
                    currentEffect.perform(die);
                    checkHasNextEffect();
                    break;
            }

        }
    }

    public void checkHasNextEffect(){
        if(effectIterator.hasNext() && everythingOk){
           performEffect();
        }
        else {endToolCard();}
    }

    private void endToolCard() {
       // gameManager.getServer().notifyPlayerIfToolCardWorked(everythingOk);
        if(everythingOk){
            gameManager.getRound().getTurn().setToolCardUsed();
            player.removeTokens(getCost());
            addTokens();
            used = true;
        }
    }


    public int getTokens() {
        return tokens;
    }


    //METHODS FOR EFFECTS
    public boolean addDieToDiceBag(Die die) {
        return gameManager.getBoard().getDiceBag().addDie(die);
    }



    public void processMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place) {
        this.number=number;
        this.color=color;
        this.adjacency=adjacency;
        this.place=place;
        setNewCoordinates();
    }

    public void getDieFromDicePool() {
        die = gameManager.getBoard().getDiceBag().draftDie();
    }

    public void chooseDieFromWindowPattern() {}

    public void chooseDieFromDraftPool() {
    } //TODO

    public void chooseDieFromRoundTrack() {
    } //TODO

    public void chooseIfDecrease() {
    } //TODO

    public void chooseIfPlaceDie() {
    } //TODO

    public void chooseToMoveOneDie(){
        //if he chooses to move just one die everythingIsOk is set to false so the tool card won't keep performing effects
    }//TODO

    public void setValue() {} //TODO

    public void setOldCoordinates() {
    } //TODO

    public void setNewCoordinates() {
    } //TODO

    public void completeProcessMove(int newRow, int newColumn, int oldRow, int oldColumn){
        MoveValidator moveValidator = new MoveValidator(gameManager.getRound().getTurn(), gameManager.getRound().getDraftPool(), number, color, adjacency);
        if (moveValidator.validateMove(die, newRow, newColumn, player)) {
            if (!adjacency || place) {
                player.getPlayerWindow().addDie(die, newRow, newColumn);
                gameManager.getRound().getTurn().setDiePlaced();
            } else {
                player.getPlayerWindow().moveDie(oldRow, oldColumn, newRow, newColumn);
            }
            everythingOk = true;
        } else {
        everythingOk = false;
        }
    }

    public void completeChooseDieFromWindowPattern(int oldRow, int oldColumn){
        die = player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
        everythingOk = true;
    }

    public void setResponse(boolean resultEffect){
        everythingOk=resultEffect;
    }

    public void completeChooseValue(int value){
        die.setNumber(value);
        everythingOk = true;
    }
}
