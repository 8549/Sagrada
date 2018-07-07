package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SagradaColor;
import it.polimi.ingsw.network.client.ClientHandler;

public interface UI {
    /**
     * This method is called when the client couln't login; it informs the user and asks again for the login data
     */
    void failedLogin();

    /**
     * This method asks for the login data, such as username, server address, connection type, etc.
     */
    void showLogin();

    /**
     * This method shows the possible pattern card choices to the user
     *
     * @param one of the two {@Link PatternCard}s (it contains both the front and the back schemes)
     * @param two same as above
     */
    void showPatternCardsChooser(PatternCard one, PatternCard two);

    /**
     * This method is called if the login was successful; it shows currently logged in users, and updates accordingly
     * to other client(s) connecting and disconnecting.
     */
    void showLoggedInUsers();

    ClientHandler getClientHandler();

    ProxyModel getModel();

    /**
     * This method sets the {@link ProxyModel} to the UI after launching the client from a different entrypoint, e.g.
     * when a reconnection occurs
     *
     * @param model the model instance
     */
    void setModelAfterReconnecting(ProxyModel model);

    /**
     * Entrypoint for UI implementations; based on the implementation, this method could or could not return.
     */
    void initUI();

    /**
     * This method is called when all the players have chose their pattern cards and have received their private
     * objective card; it sets up the ui with the players' boards, draftpool, etc.
     */
    void startGame();

    /**
     * This method triggers an update of the user interface (e.g. something in the model has changed after using a tool card
     * or after placing a die)
     */
    void update();

    /**
     * This method is called when our turn is starting; UI implementations should enable the user input
     */
    void myTurnStarted();

    /**
     * This method is called when our turn is ended; it is useful to check whether the server timer has ended or not
     */
    void myTurnEnded();

    /**
     * This method is call when both the player has already placed the die (but not used the tool yet)
     * and when he has used the tool card (but not already placed the die).
     * It asks the user if he wants to to another move
     */
    void nextMove();

    /**
     * This method contains the response from server if the selected tool il available
     * (checks the tokens)
     *
     * @param isAvailable
     */
    void toolAvailable(boolean isAvailable);

    /**
     * Handles a player disconnection
     *
     * @param p The player that has disconnected from the game
     */
    void playerDisconnected(Player p);

    /**
     * Game board initialization, entrypoint for a new match
     */
    void initBoard();

    /**
     * This method returns a boolean flag
     *
     * @return true if the UI implementation is some sort of GUI (which needs operations to be run on a specific thread)
     */
    boolean isGUI();

    /**
     * Informs the user if his move succeeded or failed
     */
    void wrongMove();

    /**
     * This method must let the player choose a die from his/her windowPattern
     */
    void chooseDieFromWindowPattern();

    /**
     * This method must let the player choose a die from current Draft Pool
     */
    void chooseDieFromDraftPool();

    /**
     * This method must let the player choose a die from the current RoundTrack
     */
    void chooseDieFromRoundTrack();

    /**
     * This method must let the player decides if he wants to increse the value of the chosen die, or decrease
     */
    void chooseIfDecrease();

    /**
     * Chooses if the player wants to place the die or to put it back in the draft pool
     * @param number
     */
    void chooseIfPlaceDie(int number);

    /**
     * Let the user choose if he wants to place the die or put i back in the draft pool
     */
    void chooseToMoveOneDie();

    /**
     * Choose a number value to give to one die
     */
    void setValue(SagradaColor color);

    /**
     * Let user choose a new position for a die
     */
    void setNewCoordinates();

    /**
     * Let the user choose two dice to move
     */
    void chooseTwoDice();

    /**
     * Let the user choose two new position
     */
    void chooseTwoCoordinates();

    /**
     * Inform the user if the Tool Card he tried to use completed its job with success or not
     *
     * @param success true if the Tool Card completed, false if it failed
     */
    void toolEnded(boolean success);
}
