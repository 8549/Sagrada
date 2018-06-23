package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
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

    void setProxyModel(ProxyModel model);

    ClientHandler getClientHandler();

    ProxyModel getModel();

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
     * This method refreshes the currently drawn game board
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
     * Handles a player disconnection
     *
     * @param p The player that has disconnected from the game
     */
    void playerDisconnected(Player p);

    /**
     * Game board initialization
     */
    void initBoard();

    /**
     * This method returns a boolean flag
     *
     * @return true if the UI implementation is some sort of GUI (which needs operations to be run on a specific thread)
     */
    boolean isGUI();
}
