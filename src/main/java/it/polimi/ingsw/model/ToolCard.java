package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.effect.Effect;
import it.polimi.ingsw.network.server.MainServer;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ToolCard implements Card {
    protected List<Effect> effects;
    protected GameManager gameManager;
    protected ToolCardHandler toolCardHandler;
    protected Player player;
    protected int tokens = 0;
    protected boolean used = false;
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

    public void setIsUsed(boolean isUsed) {
        this.used = isUsed;
    }

    public int getCost() {
        if (used) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Adds to the tokens the cost of the tool card
     */
    public void addTokens() {
        tokens = tokens + getCost();
    }

    /**
     * If the player hasn't already used the tool card, starts performing the effects of the tool card
     * otherwise notify the player that the tool card was already used
     *
     * @param player      that wants to use the toolcard
     * @param gameManager
     */
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

    /**
     * Perform the correct current effect of the tool card and whn it's done gooess to next effect
     */
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
                case "checkIfRoundTrackHasAnyDie":
                    currentEffect.perform(getBoard());
                    checkHasNextEffect();
                    break;
                case "checkIfThereIsAtLeastTwoDieOnWindowPatter":
                    currentEffect.perform(player);
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
                case "chooseTwoDiceFromWindowPattern":
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
                    currentEffect.perform(die, turnForRoundTrack, numberOfDieForRoundTrack, getBoard());
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

    /**
     * if the tool card has any effects left and the tool card is being used in the correct way it performs the next effect
     * otherwise it ends the tool card
     */
    public void checkHasNextEffect() {
        if (effectIterator.hasNext() && everythingOk) {
            performEffect();
        } else {
            toolCardHandler.setActive(false);
            endToolCard();
        }
    }

    /**
     * Notifies the player if the tool card worked
     * if the tool card worked it removes the gost of the tool card to the tokens of the player, sets that the player used the tool card
     * if the tool card worked and the player already did his move end turn, otherwise asks the  player for next move
     */
    public void endToolCard() {
        getServer().notifyPlayerIfToolCardWorked(everythingOk);
        if (everythingOk) {
            getTurn().setToolCardUsed();
            player.removeTokens(getCost());
            addTokens();
            toolCardHandler.pushNewTokens(getCost(), player, getName());
            System.out.println("Pushed tokens of " + player.getName() + " n°: " + getCost() + " for used tool: " + getName());
            used = true;
        }
        if (getTurn().isDiePlaced() && everythingOk) {
            gameManager.endCurrentTurn();
        } else {
            getServer().askPlayerForNextMove();
        }
        clearData();

    }


    public int getTokens() {
        return tokens;
    }


    //METHODS FOR EFFECTS

    /**
     * Adds the given die to the dicebag
     *
     * @param die
     * @return true if it was possible to add the die, false otherwise
     */
    public boolean addDieToDiceBag(Die die) {
        return getBoard().getDiceBag().addDie(die);
    }


    /**
     * Set what need to be check for validating the move and asks the player for the coordinates where he wants to put the die
     *
     * @param number:   if the number constraint need to be check
     * @param color     : if the color constraint need to be check
     * @param adjacency : if the adjacency constraint need to be check
     * @param place     : if the dies needs to be placed
     */
    public void processMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place) {
        this.number = number;
        this.color = color;
        this.adjacency = adjacency;
        this.place = place;
        setNewCoordinates();
    }

    /**
     * Set what need to be check for validating the move and asks the player for the two coordinates where he wants to put the die
     *
     * @param number:   if the number constraint need to be check
     * @param color     : if the color constraint need to be check
     * @param adjacency : if the adjacency constraint need to be check
     * @param place     : if the dies needs to be placed
     */
    public void processTwoMoveWithoutConstraints(boolean number, boolean color, boolean adjacency, boolean place) {
        this.number = number;
        this.color = color;
        this.adjacency = adjacency;
        this.place = place;
        setTwoNewCoordinates();
    }

    /**
     * Asks the tool card handler to set the two new coordinates
     */
    private void setTwoNewCoordinates() {
        toolCardHandler.setTwoNewCoordinates();
    }

    /**
     * Asks the tool card handler to pick a color of the die taken from the draft pool
     */
    public void getDieFromDicePool() {
        die = getBoard().getDiceBag().draftDie();
        toolCardHandler.setColorOfPickedDie(die.getColor());
    }

    /**
     * Asks the tool card handler to choose a die die the window pattern
     */
    public void chooseDieFromWindowPattern() {
        toolCardHandler.chooseDieFromWindowPattern();
    }

    /**
     * Asks the tool card handler to choose two dice from the window pattern
     */
    public void chooseTwoDieFromWindowPatter() {
        toolCardHandler.chooseTwoDieFromWindowPatter();
    }

    /**
     * Asks the tool card handler to choose a die from the draft pool
     */
    public void chooseDieFromDraftPool() {
        toolCardHandler.chooseDieFromDraftPool();
    }

    /**
     * Asks the tool card handler to choose  a die from the round track
     */
    public void chooseDieFromRoundTrack() {
        toolCardHandler.chooseDieFromRoundTrack();
    }

    /**
     * Asks the tool card handler to choose  if decrease or increase te value of the die previously chosen
     */
    public void chooseIfDecrease() {
        toolCardHandler.chooseIfDecrease();
    }

    /**
     * Asks the tool card handler to choose  if place the die in the draft pool or on the window pattern
     */
    public void chooseIfPlaceDie() {
        toolCardHandler.chooseIfPlaceDie();
    }

    /**
     * Asks the tool card handler to choose  if to move one die or two
     */
    public void chooseToMoveOneDie() {
        //if he chooses to move just one die everythingIsOk is set to false so the tool card won't keep performing effects
        toolCardHandler.chooseToMoveOneDie();
    }

    /**
     * Asks the tool card handler to set the value die
     */
    public void setValue() {
        toolCardHandler.setValue();
    }

    /**
     * Asks the tool card handler to set the number of the new coordinates
     */
    public void setNewCoordinates() {
        toolCardHandler.setNewCoordinates();
    }

    /**
     * Checks if the move is valid, if it is:
     * If the die needs to be placed, add the die to the window pattern, otherwise move the die
     * if the move is not valid set everything ok to false to terminate the tool card
     *
     * @param newRow
     * @param newColumn
     */
    public void completeProcessMove(int newRow, int newColumn) {
        if (!place) {
            if (player.getPlayerWindow().dieCount() == 1) {
                player.getPlayerWindow().setOneDie(true);
            }
        }
        MoveValidator moveValidator = new MoveValidator(getTurn(), number, color, adjacency);
        if (!place) {
            player.getPlayerWindow().getDiceGrid()[oldRow][oldColumn].removeDie();
        }
        if (moveValidator.validateMove(die, newRow, newColumn, player)) {
            if (!adjacency || place) {
                player.getPlayerWindow().addDie(die, newRow, newColumn);
                getTurn().setDiePlaced();
                toolCardHandler.notifyAddDie(player, die, newRow, newColumn);
                getTurn().setDiePlaced();
            } else if (!place) {
                player.getPlayerWindow().moveDie(oldRow, oldColumn, newRow, newColumn);
                toolCardHandler.notifyMoveDie(player, die, oldRow, oldColumn, newRow, newColumn);
                player.getPlayerWindow().setOneDie(false);
            }
            everythingOk = true;
        } else {
            everythingOk = false;

            if (!place) {
                player.getPlayerWindow().addDie(die, oldRow, oldColumn);
            }

            if (place) {
                getBoard().getDraftPool().add(die);
                toolCardHandler.updateDraftPool(getBoard().getDraftPool());
                if (adjacency){
                    everythingOk= true;
                }
            }


            if (!firstChoice) {
                everythingOk = true;
                toolCardHandler.notifyPLayerOnlyOneDieWasMoved();
            }

        }
        checkHasNextEffect();

    }

    /**
     * Assign to die the die at the given coordinates and calls the method that checks if there are any effects left
     *
     * @param oldRow
     * @param oldColumn
     */
    public void completeChooseDieFromWindowPattern(int oldRow, int oldColumn) {
        die = player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
        everythingOk = true;
    }

    /**
     * Assign to secondDie the die at the given coordinates and  calls the method that checks if there are any effects left
     *
     * @param oldRow
     * @param oldColumn
     */
    public void completeChooseTwoDieFromWindowPattern(int oldRow, int oldColumn) {
        secondDie = player.getPlayerWindow().getCellAt(oldRow, oldColumn).getDie();
        oldColumnSecond = oldColumn;
        oldRowSecond = oldRow;
    }

    public void setResponse(boolean resultEffect) {
        everythingOk = resultEffect;
    }


    /**
     * Assign to die the chosen value and  calls the method that checks if there are any effects left
     * @param value
     */
    public void completeChooseValue(int value) {
        die.setNumber(value);
        everythingOk = true;
        placeDie = true;
        checkHasNextEffect();
    }

    /**
     * Assign to die the die choosen from he draft pool and calls the method that checks if there are any effects left
     * @param die
     */
    public void completeChooseDieFromDraftPool(Die die) {
        this.die = die;
        checkHasNextEffect();
    }

    /**
     *
     *  Assign to decrease the choice of the player who wants to increase of decrease the value of the die
     *  and calls the method that checks if there are any effects left
     * @param choice : true if the player wants to decrease the value, false to increase
     */
    public void completeChoiceIfDecrease(boolean choice) {
        decrease = choice;
        checkHasNextEffect();
    }

    /**
     *  Assign to placeDie the choice of the player wants to place the die or place it in the draftpool and calls the method that checks if there are any effects left
     * @param choice : true if the player wants to place the die, false if he wants to place it in the draftpool
     */
    public void completeChoiceIfPlaceDie(boolean choice) {
        placeDie = choice;
        checkHasNextEffect();
    }

    /**
     *  Assign to moveDie the choice of the player who wants to move or place and calls the method that checks if there are any effects left
     *
     * @param choice : true if the player wants to move one die, false if the player wants tto move two
     */
    public void completeChoiceIfMoveOneDie(boolean choice) {
        moveOneDie = choice;
        if (!moveOneDie){
            everythingOk = true;
            endToolCard();
        }else {
            firstChoice=false;
            checkHasNextEffect();
        }

    }

    /**
     * Assign to parameters the coordinates of the die the player wants to move or place and calls the method that checks if there are any effects left
     *
     * @param row
     * @param column
     */
    public void completeSetOldCoordinates(int row, int column) {
        oldColumn = column;
        oldRow = row;
        checkHasNextEffect();
    }

    /**
     * Completes the choice of the die of the roundtrack assigning to die the die at the given position
     *
     * @param round                    : round where the die from the roundtrack is
     * @param numberOfDieForRoundTrack : iy's position on the round
     */
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

    /**
     * Check if its possible to move both dice following the pattern and adjacency restriction
     * if it is move both dice, otherwise notifies the tool card something went wrong
     *
     * @param row
     * @param column
     * @param secondRow
     * @param secondColumn
     */
    public void completeProcessTwoMoves(int row, int column, int secondRow, int secondColumn) {
        MoveValidator moveValidator = new MoveValidator(getTurn(), number, color, adjacency);
        if (player.getPlayerWindow().dieCount() == 2) {
            player.getPlayerWindow().setOneDie(true);
        }
        player.getPlayerWindow().getDiceGrid()[oldRow][oldColumn].removeDie();
        if (moveValidator.validateMove(die, row, column, player)) {
            player.getPlayerWindow().getDiceGrid()[oldRow][oldColumn].setDie(die);
            player.getPlayerWindow().moveDie(oldRow, oldColumn, row, column);
            player.getPlayerWindow().setOneDie(false);
            player.getPlayerWindow().getDiceGrid()[oldRowSecond][oldColumnSecond].removeDie();
            if (moveValidator.validateMove(secondDie, secondRow, secondColumn, player)) {
                player.getPlayerWindow().getDiceGrid()[oldRowSecond][oldColumnSecond].removeDie();
                player.getPlayerWindow().moveDie(oldRowSecond, oldColumnSecond, secondRow, secondColumn);
                toolCardHandler.notifyMoveDie(player, die, oldRow, oldColumn, row, column);
                toolCardHandler.notifyMoveDie(player, secondDie, oldRowSecond, oldColumnSecond, secondRow, secondColumn);
                everythingOk = true;
            } else {
                player.getPlayerWindow().moveDie(row, column, oldRow, oldColumn);
                everythingOk = false;
            }
        } else {
            everythingOk = false;
        }
        checkHasNextEffect();
    }

    public boolean isEverythingOk() {
        return everythingOk;
    }
}
