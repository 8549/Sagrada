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
    protected List<Effect> effects;
    protected GameManager gameManager;
    protected ToolCardHandler toolCardHandler;
    protected Player player;
    protected int tokens = 0;
    protected boolean used;
    protected String name;
    protected int id;
    protected String when;

    //attributes for effects
    protected Die die;
    protected Die secondDie;
    protected int turnForRoundTrack;
    protected int numberOfDieForRoundTrack;
    protected int oldRow;
    protected int oldColumn;
    protected int oldRowSecond;
    protected int oldColumnSecond;
    protected boolean decrease;
    protected boolean placeDie;
    protected boolean moveOneDie;
    protected boolean number;
    protected boolean color;
    protected boolean adjacency;
    protected boolean place;
    protected Iterator effectIterator;
    protected boolean everythingOk;
    protected boolean firstChoice;

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

    public boolean isUsed() {
        return used;
    }

    public void setIsUsed(boolean isUsed){
        this.used = isUsed;
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
        this.player = player;
        this.gameManager = gameManager;
        for (Effect effect : effects) {
            effect.setToolCard(this);
        }
        if (!getTurn().isToolCardUsed()) {
            if (getTurn().getPlayer().getTokens() >= getCost()) {
                effectIterator = effects.iterator();
                everythingOk = true;
                firstChoice = true;
                toolCardHandler = new ToolCardHandler(player, gameManager, getServer(), this);
                getServer().addToolCardHandler(toolCardHandler);
                toolCardHandler.setActive(true);
                performEffect();
            }
        } else {
            getServer().notifyPlayerToolCardAlreadyUsed();
        }

    }

    public void performEffect() {
        for (Effect effect : effects) {
            effect.setToolCard(this);
        }
        Effect currentEffect;
        if (effectIterator.hasNext() && everythingOk) {
            currentEffect = (Effect) effectIterator.next();
            switch (currentEffect.getName()) {
                case "addDieToDicePool":
                    currentEffect.perform(die);
                    checkHasNextEffect();
                    break;
                case "changeTurnOrder":
                    currentEffect.perform(getRound());
                    checkHasNextEffect();
                    break;
                case "checkIfDieHasBeenPlaced":
                    currentEffect.perform(getTurn());
                    checkHasNextEffect();
                    break;
                case "checkIsDiePlaced":
                    currentEffect.perform(getTurn());
                    checkHasNextEffect();
                    break;
                case "checkIsFirstTurn":
                    currentEffect.perform(getRound());
                    checkHasNextEffect();
                    break;
                case "checkIsSecondTurn":
                    currentEffect.perform(getRound());
                    checkHasNextEffect();
                    break;
                case "chooseDieFromDraftPool":
                    currentEffect.perform();
                    break;
                case "chooseDieFromRoundTrack":
                    currentEffect.perform();
                    break;
                case "chooseDieFromWindowPattern":
                    currentEffect.perform(firstChoice, placeDie);
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
                case "chooseTwoDiceFromWindowPattern":
                    currentEffect.perform();
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
                    currentEffect.perform(getTurn(), placeDie);
                    break;
                case "placeDieInDraftPool":
                    currentEffect.perform(placeDie, getBoard(), die);
                    checkHasNextEffect();
                    break;
                case "placeDieWithoutAdjacencyConstraint":
                    currentEffect.perform();
                    break;
                case "replaceDieOnRoundTrack":
                    currentEffect.perform(die, turnForRoundTrack, numberOfDieForRoundTrack);
                    checkHasNextEffect();
                    break;
                case "rollAllDice":
                    currentEffect.perform(getRound());
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
        getServer().notifyPlayerIfToolCardWorked(everythingOk);
        if (everythingOk) {
            getTurn().setToolCardUsed();
            player.removeTokens(getCost());
            addTokens();
            toolCardHandler.pushNewTokens(getCost(), player, getName());
            used = true;
            if (getTurn().isDiePlaced()) {
                gameManager.endCurrentTurn();
            } else {
                getServer().askPlayerForNextMove();
            }
        }
        clearData();

    }


    public int getTokens() {
        return tokens;
    }


    //METHODS FOR EFFECTS
    public boolean addDieToDiceBag(Die die) {
        return getBoard().getDiceBag().addDie(die);
    }


    public void processMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place) {
        this.number = number;
        this.color = color;
        this.adjacency = adjacency;
        this.place = place;
        setNewCoordinates();
    }

    public void processTwoMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place) {
        this.number = number;
        this.color = color;
        this.adjacency = adjacency;
        this.place = place;
        setTwoNewCoordinates();
    }

    private void setTwoNewCoordinates() {
        toolCardHandler.setTwoNewCoordinates();
    }

    public void getDieFromDicePool() {
        die = getBoard().getDiceBag().draftDie();
    }

    public void chooseDieFromWindowPattern() {
        toolCardHandler.chooseDieFromWindowPattern();
    }

    public void chooseTwoDieFromWindowPatter() {
        toolCardHandler.chooseTwoDieFromWindowPatter();
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

    public void setNewCoordinates() {
        toolCardHandler.setNewCoordinates();
    }

    public void completeProcessMove(int newRow, int newColumn) {
        MoveValidator moveValidator = new MoveValidator(getTurn(), getRound().getDraftPool(), number, color, adjacency);
        if (moveValidator.validateMove(die, newRow, newColumn, player)) {
            if (!adjacency || !place) {
                player.getPlayerWindow().addDie(die, newRow, newColumn);
                getTurn().setDiePlaced();
            } else if (adjacency || place) {
                player.getPlayerWindow().moveDie(oldRow, oldColumn, newRow, newColumn);
            }
            everythingOk = true;
        } else {
            everythingOk = false;
            if (adjacency || place) {
                getBoard().getDraftPool().add(die);
            }
        }
        checkHasNextEffect();

    }

    public void completeChooseDieFromWindowPattern(int oldRow, int oldColumn) {
        die = player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
        everythingOk = true;
        firstChoice = false;
    }

    public void completeChooseTwoDieFromWindowPattern(int oldRow, int oldColumn) {
        secondDie = player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
        oldColumnSecond = oldColumn;
        oldRowSecond = oldRow;
    }

    public void setResponse(boolean resultEffect) {
        everythingOk = resultEffect;
    }

    public void completeChooseValue(int value) {
        die.setNumber(value);
        everythingOk = true;
        checkHasNextEffect();
    }

    public void completeChooseDieFromDraftPool(Die die) {
        this.die = die;
        checkHasNextEffect();
    }

    public void completeChoiceIfDecrease(boolean choice) {
        decrease = choice;
        checkHasNextEffect();
    }

    public void completeChoiceIfPlaceDie(boolean choice) {
        placeDie = choice;
        checkHasNextEffect();
    }

    public void completeChoiceIfMoveOneDie(boolean choice) {
        moveOneDie = choice;
        checkHasNextEffect();
    }

    public void completeSetOldCoordinates(int row, int column) {
        oldColumn = column;
        oldRow = row;
        checkHasNextEffect();
    }

    public void completeChooseDieRoundTrck(int round, int numberOfDieForRoundTrack) {
        turnForRoundTrack = round;
        this.numberOfDieForRoundTrack = numberOfDieForRoundTrack;
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

    public Turn getTurn() {
        return gameManager.getRound().getTurn();
    }

    public Round getRound() {
        return gameManager.getRound();
    }

    public Board getBoard() {
        return gameManager.getBoard();
    }

    public MainServer getServer() {
        return gameManager.getServer();
    }

    public void clearData() {
        die = null;
        turnForRoundTrack = -1;
        numberOfDieForRoundTrack = -1;
        oldRow = -1;
        oldColumn = -1;
        decrease = true;
        placeDie = true;
        moveOneDie = false;
        number = true;
        color = true;
        adjacency = true;
        place = true;
        effectIterator = null;
        everythingOk = true;
    }

    public Die getDie() {
        return die;
    }

    public void completeProcessTwoMoves(int row, int column, int secondRow, int secondColumn) {
        MoveValidator moveValidator = new MoveValidator(getTurn(), getRound().getDraftPool(), number, color, adjacency);
        if (moveValidator.validateMove(die, row, column, player)) {
            if (moveValidator.validateMove(secondDie, secondRow, secondColumn, player)) {
                player.getPlayerWindow().moveDie(oldRow, oldColumn, row, column);
                player.getPlayerWindow().moveDie(oldRowSecond, oldColumnSecond, secondRow, secondColumn);
                everythingOk = true;
            }
        } else {
            everythingOk = false;
        }
        checkHasNextEffect();
    }
}
