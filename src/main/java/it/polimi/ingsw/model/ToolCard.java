package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.effect.Effect;
import it.polimi.ingsw.network.server.MainServer;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

//TODO: code to reset all the parameters for the effects after the tool card has finished
public class ToolCard implements Card {
    private List<Effect> effects;
    private GameManager gameManager;
    private ToolCardHandler toolCardHandler;
    private Player player;
    private int tokens = 0;
    private boolean used;
    private String name;
    private int id;
    private String when;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolCard toolCard = (ToolCard) o;
        return id == toolCard.id &&
                Objects.equals(effects, toolCard.effects) &&
                Objects.equals(name, toolCard.name) &&
                Objects.equals(when, toolCard.when);
    }

    @Override
    public int hashCode() {

        return Objects.hash(effects, name, id, when);
    }

    /*   public ToolCard(String name, int id, String when, List<Effect> effects) {
           this.name=name;
           this.id=id;
           this.when=when;
           this.effects= effects;
           effectIterator = effects.iterator();
           everythingOk=true;
       }
   */
    public void initReferences(GameManager gm) {
        this.gameManager = gm;
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

    public void useTools(Player player, GameManager gameManager) {
        if (!gameManager.getRound().getTurn().isToolCardUsed()) {
            if (gameManager.getRound().getTurn().getPlayer().getTokens() < getCost()) {
                effectIterator = effects.iterator();
                everythingOk = true;
                this.player = player;
                this.gameManager = gameManager;
                toolCardHandler = new ToolCardHandler(player, gameManager, gameManager.getServer(), this);
                gameManager.getServer().addToolCardHandler(toolCardHandler);
                toolCardHandler.setActive(true);
                performEffect();
            }
        } else {
            gameManager.getServer().notifyPlayerToolCardAlreadyUsed();
        }

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
                case "checkIfDieHasBeenPlaced":
                    currentEffect.perform(gameManager.getRound().getTurn());
                    checkHasNextEffect();
                    break;
                case "checkIsDiePlaced":
                    currentEffect.perform(gameManager.getRound().getTurn());
                    checkHasNextEffect();
                    break;
                case "checkIsFirstTurn":
                    currentEffect.perform(gameManager.getRound());
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
                case "chooseIfDecreaseOrIncreaseValue":
                    currentEffect.perform();
                    break;
                case "chooseIfPlaceDieOrPlaceDieInDraftPool":
                    currentEffect.perform();
                    break;
                case "chooseToMoveOneOrTwoDice":
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
                case "getDieFromDicePool":
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
                    currentEffect.perform(gameManager.getRound().getTurn(), placeDie);
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

    public void checkHasNextEffect() {
        if (effectIterator.hasNext() && everythingOk) {
            performEffect();
        } else {
            toolCardHandler.setActive(false);
            endToolCard();
        }
    }

    private void endToolCard() {
        gameManager.getServer().notifyPlayerIfToolCardWorked(everythingOk);
        if (everythingOk) {
            gameManager.getRound().getTurn().setToolCardUsed();
            player.removeTokens(getCost());
            addTokens();
            used = true;
            if (gameManager.getRound().getTurn().isDiePlaced()) {
                gameManager.endCurrentTurn();
            } else {
                gameManager.getServer().askPlayerForNextMove();
            }
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
        this.number = number;
        this.color = color;
        this.adjacency = adjacency;
        this.place = place;
        setNewCoordinates();
    }

    public void getDieFromDicePool() {
        die = gameManager.getBoard().getDiceBag().draftDie();
    }

    public void chooseDieFromWindowPattern() {
        toolCardHandler.chooseDieFromWindowPattern();
    }

    public void chooseDieFromDraftPool() {
        toolCardHandler.chooseDieFromDraftPool();
    }

    public void chooseDieFromRoundTrack() {
        toolCardHandler.chooseDieFromRoundTrack();
    }

    public void chooseIfDecrease() {
        toolCardHandler.chooseIfDecrease();
    }

    public void chooseIfPlaceDie() {
        toolCardHandler.chooseIfPlaceDie();
    }

    public void chooseToMoveOneDie() {
        //if he chooses to move just one die everythingIsOk is set to false so the tool card won't keep performing effects
        toolCardHandler.chooseToMoveOneDie();
    }

    public void setValue() {
        toolCardHandler.setValue();
    }

    public void setOldCoordinates() {
        toolCardHandler.setOldCoordinates();
    }

    public void setNewCoordinates() {
        toolCardHandler.setNewCoordinates();
    }

    public void completeProcessMove(int newRow, int newColumn, int oldRow, int oldColumn) {
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

    public void completeChooseDieFromWindowPattern(int oldRow, int oldColumn) {
        die = player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
        everythingOk = true;
    }

    public void setResponse(boolean resultEffect) {
        everythingOk = resultEffect;
    }

    public void completeChooseValue(int value) {
        die.setNumber(value);
        everythingOk = true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setParameters(String name, int id, String when, List<Effect> effects) {
        this.name = name;
        this.id = id;
        this.when = when;
        this.effects = effects;
    }

    public ToolCardHandler getToolCardHandler() {
        return toolCardHandler;
    }

    public boolean isEverythingOk(){return everythingOk;}
}
